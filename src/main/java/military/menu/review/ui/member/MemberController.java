package military.menu.review.ui.member;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.member.Member;
import military.menu.review.security.CurrentMember;
import military.menu.review.application.member.MemberService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity login(@CurrentMember Member member) {
        EntityModel model = EntityModel.of(new MemberResponse(member));
        model.add(Link.of("http://localhost:8080/docs/index.html#resources-login-member").withRel("profile"));
        return ResponseEntity.ok(model);
    }

    @PostMapping("/join")
    public ResponseEntity<EntityModel> join(@Valid @RequestBody MemberRequest memberRequest, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Member member = memberService.join(memberRequest.toEntity());
        EntityModel model = EntityModel.of(new MemberResponse(member));
        model.add(linkTo(methodOn(MemberController.class).login(null)).withRel("login"));
        model.add(Link.of("http://localhost:8080/docs/index.html#resources-join-member").withRel("profile"));
        return ResponseEntity.ok(model);
    }
}
