package military.menu.review.domain.menu;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class MenuDto {
    private Long id;
    private String name;
    private double kcal;
    private long like;
    private boolean isLiked;
    private boolean isLogin;
}
