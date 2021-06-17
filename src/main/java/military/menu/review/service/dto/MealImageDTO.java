package military.menu.review.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class MealImageDTO {
    private Long mealId;
    private MultipartFile file;
}
