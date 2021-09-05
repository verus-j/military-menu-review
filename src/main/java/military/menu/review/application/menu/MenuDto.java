package military.menu.review.application.menu;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class MenuDto {
    private Long id;
    private String name;
    private double kcal;
    private long like;
    private boolean isLiked;
}
