package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.repository.MenuRepository;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public Menu findByName(String name) {
        return menuRepository.findByName(name);
    }

    public List<MenuDTO> findByMemberLikedAndDate(Member member, LocalDate date) {
        return menuRepository.findByMemberLikedAndDate(member.getUsername(), date).stream().map(MenuDTO::new).collect(toList());
    }

    public List<MenuDTO> findByMemberLikedAndDateBetween(Member member, LocalDate start, LocalDate end) {
        return menuRepository.findByMemberLikedAndDateBetween(member.getUsername(), start, end).stream().map(MenuDTO::new).collect(toList());
    }
}
