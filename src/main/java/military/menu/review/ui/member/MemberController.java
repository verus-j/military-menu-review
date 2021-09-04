package military.menu.review.ui.member;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.member.Member;
import military.menu.review.security.CurrentMember;
import military.menu.review.application.member.MemberService;
import org.springframework.hateoas.EntityModel;
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
    private final MemberRequestValidator validator;

    @PostMapping("/login")
    public ResponseEntity login(@CurrentMember Member member) {
        return ResponseEntity.ok(new MemberResponse(member));
    }

    @PostMapping("/join")
    public ResponseEntity<EntityModel> join(@Valid @RequestBody MemberRequest memberRequest, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        validator.validate(memberRequest, errors);
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Member member = memberService.join(memberRequest.toEntity());
        EntityModel model = EntityModel.of(new MemberResponse(member));
        model.add(linkTo(methodOn(MemberController.class).login(null)).withRel("login"));
        return ResponseEntity.ok(model);
    }
}
