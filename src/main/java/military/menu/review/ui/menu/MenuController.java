package military.menu.review.controller.menu;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuRepository menuRepository;

    @GetMapping
    public ResponseEntity menus(Pageable pageable, PagedResourcesAssembler<Menu> assembler) {
        Page<Menu> menu = menuRepository.findAll(pageable);
        PagedModel<MenuResponse> result = assembler.toModel(menu, m -> new MenuResponse(m));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/like")
    public ResponseEntity like(@RequestBody Map<String, Long> map) {
        Long mealId = map.get("mealId");
        Long menuId = map.get("menuId");
        return ResponseEntity.ok("success");
    }

    @PostMapping("/unlike")
    public ResponseEntity unlike(@RequestBody Map<String, Long> map) {
        Long mealId = map.get("mealId");
        Long menuId = map.get("menuId");
        return ResponseEntity.ok("success");
    }
}
