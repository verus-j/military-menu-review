package military.menu.review.ui.review;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import military.menu.review.application.member.MemberService;
import military.menu.review.common.RestDocsConfiguration;
import military.menu.review.domain.meal.Meal;
import military.menu.review.domain.meal.MealRepository;
import military.menu.review.domain.meal.MealType;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.member.MemberType;
import military.menu.review.domain.review.Review;
import military.menu.review.domain.review.ReviewRepository;
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

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@Import(RestDocsConfiguration.class)
public class ReviewControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;
    @Autowired
    MealRepository mealRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EntityManager em;

    Member member1;
    Member member2;
    Meal meal;
    Review review;

    final String username = "wilgur513";
    final String password = "pass";
    final LocalDate date = LocalDate.of(2021, 9, 8);
    final MealType mealType = MealType.BREAKFAST;

    @BeforeEach
    void setUp() {
        member1 = Member.of(username, password, "wilgur513", MemberType.SOLDIER);
        member2 = Member.of("user", "pass", "user1", MemberType.SOLDIER);
        memberService.join(member1);
        memberService.join(member2);
        meal = Meal.of(date, mealType);
        mealRepository.save(meal);
    }

    @Test
    @DisplayName("리뷰 작성")
    public void createReview() throws Exception {
        mockMvc.perform(post("/meals/{id}/reviews", meal.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReviewRequest("contents")))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-review",
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 리뷰 URI")
                        ),
                        links(
                                linkWithRel("self").description("self 링크"),
                                linkWithRel("reviews").description("리뷰들 링크"),
                                linkWithRel("delete-review").description("리뷰 삭제 링크"),
                                linkWithRel("update-review").description("리뷰 수정 링크")
                        ),
                        responseFields(
                                fieldWithPath("id").description("리뷰 식별 번호"),
                                fieldWithPath("memberId").description("리뷰 작성자 식별 번호"),
                                fieldWithPath("mealId").description("리뷰 식단 식별 번호"),
                                fieldWithPath("content").description("리뷰 내용"),
                                fieldWithPath("created").description("리뷰 작성 시간"),
                                fieldWithPath("_links.self.href").description("self 링크"),
                                fieldWithPath("_links.reviews.href").description("리뷰 페이지 조회 링크"),
                                fieldWithPath("_links.delete-review.href").description("리뷰 제거 링크"),
                                fieldWithPath("_links.update-review.href").description("리뷰 수정 링크")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("미 로그인 시 리뷰 작성 실패")
    public void createReviewWithAnonymous() throws Exception {
        mockMvc.perform(post("/meals/{id}/reviews", meal.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReviewRequest("contents")))
        )
                .andDo(print())
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    @DisplayName("빈 문자열 리뷰 작성 시 실패")
    public void emptyContent() throws Exception {
        mockMvc.perform(post("/meals/{id}/reviews", meal.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReviewRequest("")))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("존재하지 않는 식단에 리뷰 작성 시 실패")
    public void emptyMealReview() throws Exception {
        mockMvc.perform(post("/meals/{id}/reviews", meal.getId() + 1111111)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReviewRequest("contents")))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("미 로그인 시 리뷰 페이지 조회")
    public void queryReviews() throws Exception {
        saveReviews();

        mockMvc.perform(get("/meals/{id}/reviews", meal.getId())
                .param("size", "3")
                .param("page", "0")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("query-reviews",
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
                        responseFields(
                                fieldWithPath("_embedded.reviewResponseList[].id").description("리뷰 식별 번호"),
                                fieldWithPath("_embedded.reviewResponseList[].mealId").description("리뷰 식단 식별 번호"),
                                fieldWithPath("_embedded.reviewResponseList[].memberId").description("리뷰 작성자 식별 번호"),
                                fieldWithPath("_embedded.reviewResponseList[].content").description("리뷰 내용 시간"),
                                fieldWithPath("_embedded.reviewResponseList[].created").description("리뷰 작성 시간"),
                                fieldWithPath("_embedded.reviewResponseList[]._links.self.href").description("self 링크"),
                                fieldWithPath("_embedded.reviewResponseList[]._links.reviews.href").description("리뷰 페이지 조회 링크"),
                                fieldWithPath("page.size").description("한 페이지에 속한 요소 개수"),
                                fieldWithPath("page.totalElements").description("전체 사용자 수"),
                                fieldWithPath("page.totalPages").description("전체 페이지 수"),
                                fieldWithPath("page.number").description("현재 페이지 번호(0부터 시작)"),
                                fieldWithPath("_links.self.href").description("현재 페이지"),
                                fieldWithPath("_links.first.href").description("시작 페이지"),
                                fieldWithPath("_links.next.href").description("다음 페이지"),
                                fieldWithPath("_links.last.href").description("마지막 페이지")
                        )
                ));
    }

    @Test
    @DisplayName("로그인 후 리뷰들 조회")
    public void queryReviewsWithMember() throws Exception {
        saveReviews();

        mockMvc.perform(get("/meals/{id}/reviews", meal.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
                .param("size", "3")
                .param("page", "0")
        )
                .andDo(print())
                .andExpect(jsonPath("_links.create-review.href").exists())
                .andExpect(jsonPath("_embedded.reviewResponseList[0]._links.update-review.href").exists())
                .andExpect(jsonPath("_embedded.reviewResponseList[0]._links.delete-review.href").exists())
        ;
    }

    @Test
    @DisplayName("리뷰 단건 조회")
    public void queryReview() throws Exception {
        saveReviews();

        mockMvc.perform(get("/meals/{id}/reviews/{reviewId}", meal.getId(), review.getId()))
                .andExpect(status().isOk())
                .andDo(document("query-review",
                        links(
                                linkWithRel("self").description("self 링크"),
                                linkWithRel("reviews").description("리뷰 페이지 조회 링크")
                        ),
                        responseFields(
                                fieldWithPath("id").description("리뷰 식별 번호"),
                                fieldWithPath("mealId").description("리뷰 식단 식별 번호"),
                                fieldWithPath("memberId").description("리뷰 작성자 식별 번호"),
                                fieldWithPath("content").description("리뷰 내용 시간"),
                                fieldWithPath("created").description("리뷰 작성 시간"),
                                fieldWithPath("_links.self.href").description("self 링크"),
                                fieldWithPath("_links.reviews.href").description("리뷰 페이지 조회 링크")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 식단에서 단건 리뷰 조회 시 실패")
    public void queryEmptyMealReview() throws Exception {
        mockMvc.perform(get("/meals/{id}/reviews/{reviewId}", meal.getId() + 1111, 1111))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }
    
    @Test
    @DisplayName("식단에서 작성되지 않은 리뷰 조회 시 실패")
    public void queryMealEmptyReview() throws Exception {
        saveReviews();

        mockMvc.perform(get("/meals/{id}/reviews/{reviewId}", meal.getId(), review.getId() + 1111))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("작성한 리뷰 삭제")
    public void deleteReview() throws Exception {
        saveReviews();

        mockMvc.perform(delete("/meals/{id}/reviews/{reviewId}", meal.getId(), review.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-review",
                        links(
                                linkWithRel("create-review").description("리뷰 생성"),
                                linkWithRel("reviews").description("리뷰 페이지 조회")
                        )
                ))
        ;

        Optional<Review> r = reviewRepository.findById(review.getId());
        assertThat(r.isPresent()).isFalse();
    }

    @Test
    @DisplayName("미 로그인 시 리뷰 삭제 시도 시 실패")
    public void deleteReviewWithAnonymous() throws Exception {
        saveReviews();

        mockMvc.perform(delete("/meals/{id}/reviews/{reviewId}", meal.getId(), review.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @DisplayName("존재하지 않는 식단에서 리뷰 삭제 시")
    public void deleteReviewFromEmptyMeal() throws Exception {
        mockMvc.perform(delete("/meals/{id}/reviews/{reviewId}", meal.getId() + 1111, 1111)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
        )
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("식단에서 작성되지 않은 리뷰 조회 시 실패")
    public void deleteNotCreatedReview() throws Exception {
        saveReviews();

        mockMvc.perform(delete("/meals/{id}/reviews/{reviewId}", meal.getId(), review.getId() + 1111)
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
        )
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("작성자 이외의 사용자가 리뷰 삭제 시 실패")
    public void deleteReviewWithNotCreator() throws Exception {
        saveReviews();

        mockMvc.perform(delete("/meals/{id}/reviews/{reviewId}", meal.getId(), review.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken("user", "pass"))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    @DisplayName("리뷰 수정")
    public void updateReview() throws Exception {
        saveReviews();

        LocalDateTime oldTime = review.getCreated();
        String oldContent = review.getContent();

        mockMvc.perform(put("/meals/{id}/reviews/{reviewId}", meal.getId(), review.getId())
                .header(HttpHeaders.AUTHORIZATION, getBearerToken(username, password))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReviewRequest("update content")))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("update-review",
                        links(
                                linkWithRel("self").description("self 링크"),
                                linkWithRel("delete-review").description("리뷰 삭제 링크"),
                                linkWithRel("update-review").description("리뷰 수정 링크"),
                                linkWithRel("reviews").description("리뷰 페이지 조회 링크")
                        ),
                        responseFields(
                                fieldWithPath("id").description("리뷰 식별 번호"),
                                fieldWithPath("mealId").description("리뷰 식단 식별 번호"),
                                fieldWithPath("memberId").description("리뷰 작성자 식별 번호"),
                                fieldWithPath("content").description("리뷰 내용 시간"),
                                fieldWithPath("created").description("리뷰 작성 시간"),
                                fieldWithPath("_links.self.href").description("self 링크"),
                                fieldWithPath("_links.reviews.href").description("리뷰 페이지 조회 링크"),
                                fieldWithPath("_links.delete-review.href").description("리뷰 삭제 링크"),
                                fieldWithPath("_links.update-review.href").description("리뷰 수정 링크")
                        )
                ));

        Optional<Review> optional = reviewRepository.findById(review.getId());
        assertThat(optional.get().getContent()).isNotEqualTo(oldContent);
        assertThat(optional.get().getCreated()).isNotEqualTo(oldTime);
    }

    private void saveReviews() {
        review = Review.of(member1, meal, "content1");
        reviewRepository.save(review);

        for(int i = 0; i < 9; i++) {
            reviewRepository.save(Review.of(member2, meal, "content" + (i + 2)));
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
