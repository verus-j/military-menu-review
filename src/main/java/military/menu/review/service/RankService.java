package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.menu.Menu;
import military.menu.review.domain.Week;
import military.menu.review.repository.menu.MenuRepository;
import military.menu.review.repository.rank.RankRepository;
import military.menu.review.service.dto.RankDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RankService {
    private final RankRepository rankRepository;
    private final MenuRepository menuRepository;

    public List<RankDTO> top10(Week week) {
        List<RankDTO> ranks = isThisWeek(week) ? makeRankFromMenuRepository() : makeRankFromRankRepository(week);
        return removeSameLikeWithLastRank(ranks);
    }

    private boolean isThisWeek(Week week) {
        Week thisWeek = Week.from(LocalDate.now());
        return thisWeek.equals(week);
    }

    private List<RankDTO> makeRankFromMenuRepository() {
        List<Menu> menus = menuRepository.findOrderByLikeLimit(11);
        List<RankDTO> ranks = new ArrayList<>();

        for(int rank = 0; rank < menus.size(); rank++) {
            ranks.add(new RankDTO(menus.get(rank), rank + 1));
        }

        return ranks;
    }

    private List<RankDTO> makeRankFromRankRepository(Week week) {
        return rankRepository.findByWeekOrderByRankLimit(week, 11)
                .stream().map(RankDTO::new).collect(Collectors.toList());
    }

    private List<RankDTO> removeSameLikeWithLastRank(List<RankDTO> ranks) {
        if(ranks.size() > 0) {
            RankDTO last = ranks.get(ranks.size() - 1);
            return ranks.stream().filter(r -> r.getLike() != last.getLike()).sorted().collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
