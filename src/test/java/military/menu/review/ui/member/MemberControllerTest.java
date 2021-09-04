package military.menu.review.ui.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import military.menu.review.domain.Role;
import military.menu.review.domain.member.Member;
import military.menu.review.security.LoginRequest;
import military.menu.review.application.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
public class MemberControllerTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("정상적인 로그인 테스트")
    @Transactional
    public void login() throws Exception {
        Member member = Member.of("wilgur513", "pass", "정진혁", null, Role.NORMAL);
        memberService.join(member);

        mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new LoginRequest("wilgur513", "pass")))
                )
                .andDo(print())
                .andExpect(jsonPath("username").value("wilgur513"))
                .andExpect(jsonPath("name").value("정진혁"))
                .andExpect(jsonPath("role").value("NORMAL"))
                .andDo(document("login-member",
                        requestFields(
                                fieldWithPath("username").description("사용자 아이디"),
                                fieldWithPath("password").description("사용자 비밀번호")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("id").description("사용자 번호"),
                                fieldWithPath("username").description("사용자 아이디"),
                                fieldWithPath("name").description("사용자 이름"),
                                fieldWithPath("militaryId").description("사용자 군번(민간인의 경우 null 값)"),
                                fieldWithPath("role").description("사용자 유형(민간인[NORMAL], 군인[SOLDIER])")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("정상적인 회원 가입 테스트")
    @Transactional
    public void join() throws Exception {
        MemberRequest memberRequest = MemberRequest.builder()
                .username("wilgur513")
                .name("정진혁")
                .password("pass")
                .militaryId("")
                .role("NORMAL")
                .build();

        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest))
                )
                .andDo(print())
                .andDo(document("join-member",
                        links(
                                linkWithRel("login").description("로그인 URI")
                        ),
                        requestFields(
                                fieldWithPath("username").description("사용자 아이디"),
                                fieldWithPath("name").description("사용자 이름"),
                                fieldWithPath("password").description("사용자 비밀번호"),
                                fieldWithPath("militaryId").description("사용자 군번(민간인의 경우 생략)"),
                                fieldWithPath("role").description("사용자 유형(민간인[NORMAL], 군인[SOLDIER])")
                        ),
                        responseFields(
                                fieldWithPath("id").description("사용자 번호"),
                                fieldWithPath("username").description("사용자 아이디"),
                                fieldWithPath("name").description("사용자 이름"),
                                fieldWithPath("militaryId").description("사용자 군번(민간인의 경우 null 값)"),
                                fieldWithPath("role").description("사용자 유형(민간인[NORMAL], 군인[SOLDIER])"),
                                fieldWithPath("_links.login.href").description("로그인 URI")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 로그인 시 실패 테스트")
    public void unauthorizedLogin() throws Exception {
        LoginRequest request = new LoginRequest("wilgur513", "pass");
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    @DisplayName("빈 값으로 회원가입 시 실패 테스트")
    public void emptyMemberInfoJoin() throws Exception{
        MemberRequest request = MemberRequest.builder().build();

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }
}
