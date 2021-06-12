package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Role;
import military.menu.review.repository.MenuRepository;
import military.menu.review.security.MemberContext;
import military.menu.review.security.MemberDTO;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public Menu findByName(String name) {
        return menuRepository.findByName(name);
    }

    public List<MenuDTO> findByMemberLikedAndDate(MemberContext context, LocalDate date) {
        MemberDTO member = context.getCurrentMember();
        return menuRepository.findByMemberLikedAndDate(member.getUsername(), date).stream().map(MenuDTO::new).collect(toList());
    }

    public List<MenuDTO> findByMemberLikedAndDateBetween(MemberContext context, LocalDate start, LocalDate end) {
        MemberDTO member = context.getCurrentMember();
        return menuRepository.findByMemberLikedAndDateBetween(member.getUsername(), start, end).stream().map(MenuDTO::new).collect(toList());
    }
}
