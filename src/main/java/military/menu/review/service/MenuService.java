package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Week;
import military.menu.review.repository.LikeRepository;
import military.menu.review.repository.MenuRepository;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    public List<MenuDTO> findByMemberLikedDuringWeek(Member member, Week week) {
        return menuRepository.findByMemberLikedDuringWeek(member, week).stream().map(MenuDTO::new).collect(toList());
    }

    public Menu findByName(String name) {
        return menuRepository.findByName(name);
    }

    public Menu findById(long id) {
        Optional<Menu> optional = menuRepository.findById(id);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new IllegalArgumentException();
    }
}
