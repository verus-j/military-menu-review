package military.menu.review.ui.menu;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuRepository;
import military.menu.review.security.CurrentMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuRepository menuRepository;

    @GetMapping
    public ResponseEntity menus(@CurrentMember Member member, Pageable pageable, PagedResourcesAssembler<Menu> assembler) {
        Page<Menu> menuPage = menuRepository.findAll(pageable);
        PagedModel<MenuResponse> result = assembler.toModel(menuPage, m -> new MenuResponse(m));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity menu( @PathVariable long id) {
        Optional<Menu> menuOptional = menuRepository.findById(id);
        if(!menuOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Menu menu = menuOptional.get();
        return ResponseEntity.ok(new MenuResponse(menu));
    }
}
