package military.menu.review.service.dto;

import lombok.Getter;
import military.menu.review.domain.Member;
import military.menu.review.domain.Review;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewPageDTO {
    private List<ReviewDTO> content;
    private int size;
    private int totalPage;
    private boolean isLastPage;

    public ReviewPageDTO(Page<Review> page, Member member) {
        this.content = page.getContent().stream().map(r -> new ReviewDTO(r, member)).collect(Collectors.toList());
        this.size = page.getSize();
        this.totalPage = page.getTotalPages();
        this.isLastPage = !page.hasNext();
    }
}
