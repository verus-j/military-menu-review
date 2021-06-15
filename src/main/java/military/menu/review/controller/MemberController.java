package military.menu.review.controller;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Member;
import military.menu.review.service.MemberService;
import military.menu.review.service.dto.MemberDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity login() {
        return ResponseEntity.ok("success");
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody MemberDTO memberDTO) {
        System.out.println(memberDTO);
        memberService.join(memberDTO.toEntity());
        return ResponseEntity.ok("success");
    }

    @GetMapping("/info")
    public ResponseEntity<MemberDTO> info() {
        Member member = memberService.getCurrentMember();
        return ResponseEntity.ok(new MemberDTO(member));
    }
}
