package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.meal.Meal;
import military.menu.review.service.dto.MealImageDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class MealImageService {
    private final MealService mealService;
    private final MealImageProperties properties;

    public void upload(MealImageDTO mealImage) {
        Meal meal = mealService.findById(mealImage.getMealId());
        Path imageFullPath = properties.imageFullPath(meal);
        File imageDir = properties.imageDir(meal);

        try {
            makeDirectoryForMealImage(imageDir);
            createNewFileForMealImage(imageFullPath);
            copyMealImageToPrevFile(mealImage.getFile(), imageFullPath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void makeDirectoryForMealImage(File dir) {
        if(!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void createNewFileForMealImage(Path file) throws IOException {
        if(isNotExist(file)) {
            file.toFile().createNewFile();
        }
    }

    private void copyMealImageToPrevFile(MultipartFile newFile, Path oldFile) throws IOException {
        Files.copy(newFile.getInputStream(), oldFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public Resource download(Long mealId) {
        Meal meal = mealService.findById(mealId);
        Path imageFullPath = properties.imageFullPath(meal);
        Path noImageFullPath = properties.noImageFullPath();

        try {
            return mealImage(imageFullPath, noImageFullPath);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Resource mealImage(Path imageFullPath, Path noImageFullPath) throws MalformedURLException {
        if(isNotExist(imageFullPath)) {
            return new UrlResource(noImageFullPath.toUri());
        }

        return new UrlResource(imageFullPath.toUri());
    }

    private boolean isNotExist(Path path) {
        return !path.toFile().exists();
    }
}
