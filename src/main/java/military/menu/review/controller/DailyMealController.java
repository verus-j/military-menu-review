package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.mnd.api.MndApi;
import military.menu.review.mnd.api.parser.DailyMealsParser;
import military.menu.review.service.DailyMealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dailyMeal")
@RequiredArgsConstructor
public class DailyMealController {
    private final MndApi api;
    private final DailyMealService dailyMealService;

    @GetMapping("/one")
    public ResponseEntity<DailyMealDTO> dailyMeal(@RequestParam String date) {
        return new ResponseEntity<>(
                dailyMealService.findByDate(toDate(date)),
                HttpStatus.OK
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<DailyMealDTO>> list(@RequestParam String start, @RequestParam String end) {
        return new ResponseEntity(
                dailyMealService.findByDateBetween(toDate(start), toDate(end)),
                HttpStatus.OK
        );
    }

    @GetMapping("/test")
    public ResponseEntity<List<DailyMealDTO>> test() {
        return new ResponseEntity(
                api.parse(new DailyMealsParser()),
                HttpStatus.OK
        );
    }

    private LocalDate toDate(String date) {
        return LocalDate.parse(date);
    }
}
