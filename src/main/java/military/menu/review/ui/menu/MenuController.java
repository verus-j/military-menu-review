package military.menu.review.ui.menu;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.menu.MenuDto;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuDao;
import military.menu.review.domain.menu.MenuRepository;
import military.menu.review.security.CurrentMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {
    private final MenuRepository menuRepository;
    private final MenuDao menuDao;

    @GetMapping
    public ResponseEntity menus(@CurrentMember Member member, @PageableDefault(sort = {"name"}) Pageable pageable, PagedResourcesAssembler<MenuDto> dtoAssembler) {
        List<MenuDto> menuDtoList = queryForList(pageable, member);
        Page<MenuDto> dtoPage = new PageImpl<>(menuDtoList, pageable, menuRepository.count());
        PagedModel<MenuResponse> result = dtoAssembler.toModel(dtoPage, MenuResponse::new);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity menu(@CurrentMember Member member, @PathVariable long id) {
        Optional<MenuDto> optional = queryByOptional(member, id);
        if(!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MenuResponse(optional.get()));
    }

    private Optional<MenuDto> queryByOptional(Member member, Long id) {
        if(member == null) {
            return menuDao.selectByIdWithIsLiked(id, null);
        }
        return menuDao.selectByIdWithIsLiked(id, member.getId());
    }

    private List<MenuDto> queryForList(Pageable pageable, Member member) {
        if(member == null) {
            return menuDao.selectAllWithIsLiked(pageable, null);
        }
        return menuDao.selectAllWithIsLiked(pageable, member.getId());
    }
}
