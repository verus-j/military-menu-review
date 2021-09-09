package military.menu.review.ui.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import military.menu.review.application.like.LikeService;
import military.menu.review.application.member.MemberService;
import military.menu.review.common.RestDocsConfiguration;
import military.menu.review.domain.member.MemberType;
import military.menu.review.domain.like.Like;
import military.menu.review.domain.like.LikeRepository;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuRepository;
import military.menu.review.security.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@Import(RestDocsConfiguration.class)
public class LikeControllerTest {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    LikeService likeService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    static final String USERNAME = "wilgur513";
    static final String PASSWORD = "pass";
    Menu menu1, menu2;
    Member member;

    @BeforeEach
    void setUp() {
        menu1 = Menu.of("a", 1.0);
        menuRepository.save(menu1);
        menu2 = Menu.of("b", 2.0);
        menuRepository.save(menu2);
        member = Member.of(USERNAME, PASSWORD, "정진혁", MemberType.SOLDIER);
        memberService.join(member);
    }

    @Test
    @DisplayName("메뉴 좋아요 누르기")
    public void likeMenu() throws Exception {
        mockMvc.perform(post("/menus/{id}/likes", menu1.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("like-menu",
                        links(
                                linkWithRel("self").description("메뉴 자신 링크"),
                                linkWithRel("cancel-like").description("메뉴 좋아요 해제 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰 값")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 리소스 링크")
                        ),
                        responseFields(
                                fieldWithPath("id").description("좋아요 식별 번호"),
                                fieldWithPath("menuId").description("좋아요 누른 메뉴 식별 번호"),
                                fieldWithPath("memberId").description("좋아요 누른 사용자 식별 번호"),
                                fieldWithPath("dateTime").description("좋아요 누른 시간"),
                                fieldWithPath("_links.self.href").description("생성된 좋아요 링크"),
                                fieldWithPath("_links.cancel-like.href").description("좋아요 해제 링크")
                        )
                ))
        ;

        Like like = likeRepository.findByMemberAndMenu(member, menu1);
        assertThat(like).isNotNull();
        assertThat(like.getMenu()).isEqualTo(menu1);
        assertThat(like.getMember()).isEqualTo(member);
        assertThat(like.getDateTime()).isNotNull();
        assertThat(like.getId()).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 메뉴에 좋아요 누르기")
    public void createLikeNotExistMenu() throws Exception {
        mockMvc.perform(post("/menus/{id}/likes", menu1.getId() + 11111)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("중복으로 좋아요 누르기 시도 시 실패")
    public void duplicateLike() throws Exception {
        likeService.like(member, menu1);
        mockMvc.perform(post("/menus/{id}/likes", menu1.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
        )
                .andDo(print())
                .andExpect(status().isConflict())
        ;
    }

    @Test
    @DisplayName("미 로그인 시 좋아요 누르기 시도 시 실패")
    public void anonymousLike() throws Exception {
        mockMvc.perform(post("/menus/{id}/likes", menu1.getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("미 로그인 시 좋아요 페이지 조회")
    public void queryLikesWithAnonymous() throws Exception {
        generateLikes();
        mockMvc.perform(get("/menus/{id}/likes", menu1.getId())
                .param("size", "3")
                .param("page", "1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page.totalElements").value(10))
                .andDo(document("query-likes-with-anonymous",
                        links(
                                linkWithRel("self").description("self 링크"),
                                linkWithRel("first").description("시작 페이지"),
                                linkWithRel("prev").description("이전 페이지"),
                                linkWithRel("next").description("다음 페이지"),
                                linkWithRel("last").description("마지막 페이지")
                        ),
                        requestParameters(
                                parameterWithName("size").description("한 페이지에 속한 요소 개수"),
                                parameterWithName("page").description("조회할 페이지 번호")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.likeResponseList[].id").description("좋아요 식별 번호"),
                                fieldWithPath("_embedded.likeResponseList[].menuId").description("좋아요 누른 메뉴 식별 번호"),
                                fieldWithPath("_embedded.likeResponseList[].memberId").description("좋아요 누른 사용자 식별 번호"),
                                fieldWithPath("_embedded.likeResponseList[].dateTime").description("좋아요 누른 시간"),
                                fieldWithPath("_embedded.likeResponseList[]._links.self.href").description("self 링크"),
                                fieldWithPath("page.size").description("한 페이지에 속한 요소 개수"),
                                fieldWithPath("page.totalElements").description("전체 사용자 수"),
                                fieldWithPath("page.totalPages").description("전체 페이지 수"),
                                fieldWithPath("page.number").description("현재 페이지 번호(0부터 시작)"),
                                fieldWithPath("_links.first.href").description("시작 페이지"),
                                fieldWithPath("_links.prev.href").description("이전 페이지"),
                                fieldWithPath("_links.self.href").description("현재 페이지"),
                                fieldWithPath("_links.next.href").description("다음 페이지"),
                                fieldWithPath("_links.last.href").description("마지막 페이지")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("로그인 후 좋아요 페이지 조회")
    public void queryLikesWithOwnerMember() throws Exception {
        generateLikes();
        mockMvc.perform(get("/menus/{menuId}/likes", menu1.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
                .param("size", "3")
                .param("page", "0")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page.totalElements").value(10))
                .andExpect(jsonPath("_embedded.likeResponseList[0]._links.cancel-like.href").exists())
                .andExpect(jsonPath("_embedded.likeResponseList[1]._links.cancel-like.href").doesNotExist())
                .andExpect(jsonPath("_embedded.likeResponseList[2]._links.cancel-like.href").doesNotExist())
                .andDo(document("query-likes-with-anonymous",
                        links(
                                linkWithRel("self").description("self 링크"),
                                linkWithRel("first").description("시작 페이지"),
                                linkWithRel("next").description("다음 페이지"),
                                linkWithRel("last").description("마지막 페이지")
                        ),
                        requestParameters(
                                parameterWithName("size").description("한 페이지에 속한 요소 개수"),
                                parameterWithName("page").description("조회할 페이지 번호")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("_embedded.likeResponseList[].id").description("좋아요 식별 번호"),
                                fieldWithPath("_embedded.likeResponseList[].menuId").description("좋아요 누른 메뉴 식별 번호"),
                                fieldWithPath("_embedded.likeResponseList[].memberId").description("좋아요 누른 사용자 식별 번호"),
                                fieldWithPath("_embedded.likeResponseList[].dateTime").description("좋아요 누른 시간"),
                                fieldWithPath("_embedded.likeResponseList[]._links.self.href").description("self 링크"),
                                fieldWithPath("page.size").description("한 페이지에 속한 요소 개수"),
                                fieldWithPath("page.totalElements").description("전체 사용자 수"),
                                fieldWithPath("page.totalPages").description("전체 페이지 수"),
                                fieldWithPath("page.number").description("현재 페이지 번호(0부터 시작)"),
                                fieldWithPath("_links.self.href").description("현재 페이지"),
                                fieldWithPath("_links.first.href").description("시작 페이지"),
                                fieldWithPath("_links.next.href").description("다음 페이지"),
                                fieldWithPath("_links.last.href").description("마지막 페이지")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 메뉴에서 좋아요 페이지 조회 시 실패")
    public void queryLikesNotExistMenu() throws Exception {
        mockMvc.perform(get("/menus/{menuId}/likes", menu1.getId() + 111111))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("미 로그인 시 좋아요 단건 조회")
    public void queryLike() throws Exception {
        Like like = likeService.like(member, menu1);
        mockMvc.perform(get("/menus/{menuId}/likes/{likeId}", menu1.getId(), like.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("query-like-with-anonymous",
                        links(
                                linkWithRel("self").description("self 링크")
                        ),
                        pathParameters(
                                parameterWithName("menuId").description("메뉴 식별 번호"),
                                parameterWithName("likeId").description("좋아요 식별 번호")
                        ),
                        responseFields(
                                fieldWithPath("id").description("좋아요 식별 번호"),
                                fieldWithPath("menuId").description("좋아요 누른 메뉴 식별 번호"),
                                fieldWithPath("memberId").description("좋아요 누른 사용자 식별 번호"),
                                fieldWithPath("dateTime").description("좋아요 누른 시간"),
                                fieldWithPath("_links.self.href").description("self 링크")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("사용자 로그인 후 본인이 생성한 좋아요 단건 조회")
    public void queryLikeWithOwnerMember() throws Exception {
        Like like = likeService.like(member, menu1);
        mockMvc.perform(get("/menus/{menuId}/likes/{likeId}", menu1.getId(), like.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("query-like-with-owner-member",
                        links(
                                linkWithRel("self").description("self 링크"),
                                linkWithRel("cancel-like").description("좋아요 해제 링크")
                        ),
                        pathParameters(
                                parameterWithName("menuId").description("메뉴 식별 번호"),
                                parameterWithName("likeId").description("좋아요 식별 번호")
                        ),
                        responseFields(
                                fieldWithPath("id").description("좋아요 식별 번호"),
                                fieldWithPath("menuId").description("좋아요 누른 메뉴 식별 번호"),
                                fieldWithPath("memberId").description("좋아요 누른 사용자 식별 번호"),
                                fieldWithPath("dateTime").description("좋아요 누른 시간"),
                                fieldWithPath("_links.self.href").description("self 링크"),
                                fieldWithPath("_links.cancel-like.href").description("좋아요 해제 링크")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("사용자 로그인 후 본인이 생성하지 않은 좋아요 단건 조회")
    public void queryLikeWithNotOwnerMember() throws Exception {
        Member m = Member.of("username", "pass", "", MemberType.SOLDIER);
        memberService.join(m);
        Like like = likeService.like(member, menu1);
        mockMvc.perform(get("/menus/{menuId}/likes/{likeId}", menu1.getId(), like.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken("username", "pass"))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("query-like-with-not-owner-member",
                        links(
                                linkWithRel("self").description("self 링크")
                        ),
                        pathParameters(
                                parameterWithName("menuId").description("메뉴 식별 번호"),
                                parameterWithName("likeId").description("좋아요 식별 번호")
                        ),
                        responseFields(
                                fieldWithPath("id").description("좋아요 식별 번호"),
                                fieldWithPath("menuId").description("좋아요 누른 메뉴 식별 번호"),
                                fieldWithPath("memberId").description("좋아요 누른 사용자 식별 번호"),
                                fieldWithPath("dateTime").description("좋아요 누른 시간"),
                                fieldWithPath("_links.self.href").description("self 링크")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 좋아요 조회 시 실패")
    public void queryNotExistLike() throws Exception {
        Like like = likeService.like(member, menu1);
        mockMvc.perform(get("/menus/{menuId}/likes/{likeId}", menu1.getId(), like.getId() + 10000))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("메뉴에 속하지 않은 좋아요 조회 시 실패")
    public void queryLikeNotIncludeMenu() throws Exception {
        Like like = likeService.like(member, menu1);
        mockMvc.perform(get("/menus/{menuId}/likes/{likeId}", menu2.getId(), like.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("존재하지 않는 메뉴에서 좋아요 조회 시 실패")
    public void queryLikeNotExistMenu() throws Exception {
        Like like = likeService.like(member, menu1);
        mockMvc.perform(get("/menus/{menuId}/likes/{likeId}", menu1.getId() + 111111, like.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("좋아요 해제")
    public void cancelLike() throws Exception {
        likeService.like(member, menu1);
        mockMvc.perform(delete("/menus/{menuId}/cancel-like", menu1.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("cancel-like",
                        links(
                                linkWithRel("like").description("좋아요 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("_links.like.href").description("좋아요 링크")
                        )
                ))
        ;

        Like like = likeRepository.findByMemberAndMenu(member, menu1);
        assertThat(like).isNull();
    }

    @Test
    @DisplayName("미 로그인 시 좋아요 해제 시 실패")
    public void cancelLikeWithAnonymous() throws Exception {
        likeService.like(member, menu1);
        mockMvc.perform(delete("/menus/{menuId}/cancel-like", menu1.getId())
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    @DisplayName("좋아요를 누르지 않은 메뉴 좋아요 해제 시 실패")
    public void cancelLikeNotExist() throws Exception {
        mockMvc.perform(delete("/menus/{menuId}/cancel-like", menu1.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
        )
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("존재하지 않는 메뉴에서 좋아요 해제 시 실패")
    public void cancelLikeNotExistMenu() throws Exception {
        mockMvc.perform(delete("/menus/{menuId}/cancel-like", menu1.getId() + 1111)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(USERNAME, PASSWORD))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    private void generateLikes() {
        likeService.like(member, menu1);
        likeService.like(member, menu2);
        for(int i = 0; i < 9; i++) {
            Member m = Member.of("username" + i, "pass", "name" + i, MemberType.SOLDIER);
            memberService.join(m);
            likeService.like(m, menu1);
        }
    }

    private String getBearerToken(String username, String password) throws Exception {
        return "Bearer " + mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new LoginRequest(username, password)))
        )
                .andReturn().getResponse().getHeader(HttpHeaders.AUTHORIZATION);
    }
}
