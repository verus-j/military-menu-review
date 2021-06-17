package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.MealImageService;
import military.menu.review.service.dto.MealImageDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meal-image")
public class MealImageController {
    private final MealImageService mealImageService;

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam Long mealId, HttpServletRequest request) {
        Resource resource = mealImageService.download(mealId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType(resource, request)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<?> upload(@ModelAttribute MealImageDTO mealImage) {
        if(mealImage.getFile().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        mealImageService.upload(mealImage);

        return ResponseEntity.ok("success");
    }

    private String contentType(Resource resource, HttpServletRequest request) {
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        return contentType == null ? "application/octet-stream" : contentType;
    }
}
