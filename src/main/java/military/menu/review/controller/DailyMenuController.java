package military.menu.review.controller;

import military.menu.review.model.menu.*;
import military.menu.review.service.DailyMenuService;
import military.menu.review.service.ReviewService;
import military.menu.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
public class DailyMenuController {
    private UserService userService;
    private ReviewService reviewService;
    private DailyMenuService service;

    @Autowired
    public DailyMenuController(ReviewService reviewService, DailyMenuService service) {
        this.reviewService = reviewService;
        this.service = service;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dailyMenu")
    public List<Map<String, Object>> dailyMenu(@RequestParam String from, @RequestParam String to) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<DailyMenu> list = service.findFromTo(LocalDate.parse(from), LocalDate.parse(to));

        for(DailyMenu dailyMenu : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", dailyMenu.getId());
            map.put("date", dailyMenu.getDate());
            map.put("breakfast", dailyMenu.getBreakfast());
            map.put("lunch", dailyMenu.getLunch());
            map.put("dinner", dailyMenu.getDinner());
            result.add(map);
        }

        return result;
    }

    @GetMapping("/review")
    public Page<Review> reviews(@RequestParam Long id, @RequestParam int page) {
        return reviewService.findByMenuList(id, PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "created")));
    }

    @GetMapping("/review/new")
    public ResponseEntity newReview(@RequestParam String content) {
        User user = userService.findByName("홍길동");
        reviewService.writeReview(user, content);
        return new ResponseEntity(HttpStatus.OK);
    }
}
