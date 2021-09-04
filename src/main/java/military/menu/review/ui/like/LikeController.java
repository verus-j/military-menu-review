package military.menu.review.ui.like;

import lombok.RequiredArgsConstructor;
import military.menu.review.application.like.exception.LikeIsAlreadyExistException;
import military.menu.review.application.like.exception.LikeIsNotExistException;
import military.menu.review.application.like.LikeService;
import military.menu.review.domain.like.Like;
import military.menu.review.domain.like.LikeRepository;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.menu.MenuRepository;
import military.menu.review.security.CurrentMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus/{menuId}")
public class LikeController {
    private final MenuRepository menuRepository;
    private final LikeService likeService;
    private final LikeRepository likeRepository;

    @ExceptionHandler(LikeIsAlreadyExistException.class)
    public ResponseEntity likeIsAlreadyExistException() {
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LikeIsNotExistException.class)
    public ResponseEntity likeIsNotExistException() {
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/likes")
    public ResponseEntity createLikes(@PathVariable Long menuId, @CurrentMember Member member) {
        if(member == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if(!menuOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Menu menu = menuOptional.get();
        Like like = likeService.like(member, menu);

        URI location = linkTo(LikeController.class, menuId).slash(like.getId()).toUri();
        return ResponseEntity.created(location).body(new LikeResponse(like, member));
    }

    @GetMapping("/likes")
    public ResponseEntity queryLikes(Pageable pageable, PagedResourcesAssembler<Like> assembler,
                                     @PathVariable Long menuId, @CurrentMember Member member) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if(!menuOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Page<Like> page = likeRepository.findAllByMenuId(menuId, pageable);
        PagedModel<LikeResponse> pagedModel = assembler.toModel(page, l -> new LikeResponse(l, member));
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/likes/{likeId}")
    public ResponseEntity queryLike(@PathVariable Long menuId, @PathVariable Long likeId, @CurrentMember Member member) {
        Optional<Like> optionalLike = likeRepository.findById(likeId);
        if(!optionalLike.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Like like = optionalLike.get();
        if(!like.getMenuId().equals(menuId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new LikeResponse(like, member));
    }

    @DeleteMapping("/likes")
    public ResponseEntity cancel(@PathVariable Long menuId, @CurrentMember Member member) {
        if(member == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if(!menuOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Menu menu = menuOptional.get();
        likeService.cancel(member, menu);

        RepresentationModel model = new RepresentationModel();
        model.add(linkTo(methodOn(LikeController.class).createLikes(menuId, member)).withRel("like"));
        return ResponseEntity.ok(model);
    }
}
