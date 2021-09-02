package military.menu.review.service;

import military.menu.review.domain.meal.Meal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Component
public class MealImageProperties {
    @Value("${meal-image.basePath}")
    private String basePath;
    @Value("${meal-image.noImagePath}")
    private String noImagePath;
    @Value("${meal-image.imageName}")
    private String imageName;

    public File imageDir(Meal meal) {
        return new File(imageDirPath(meal));
    }

    private String imageDirPath(Meal meal) {
        StringBuilder path = new StringBuilder(basePath);
        LocalDate date = meal.getDate();

        path.append("/" + date.getYear());
        path.append("/" + date.getMonthValue());
        path.append("/" + date.getDayOfMonth());
        path.append("/" + meal.getType().name());

        return path.toString();
    }

    public Path imageFullPath(Meal meal) {
        return path(imageDir(meal) + imageName);
    }

    public Path noImageFullPath() {
        return path(basePath + noImagePath);
    }

    private Path path(String path) {
        return Paths.get(path).toAbsolutePath().normalize();
    }
}
