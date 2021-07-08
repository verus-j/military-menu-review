package military.menu.review.repository.rank;

import military.menu.review.domain.Menu;
import military.menu.review.domain.Rank;
import military.menu.review.domain.Week;
import military.menu.review.repository.menu.MenuRepository;
import military.menu.review.repository.rank.RankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static military.menu.review.repository.MenuUtils.createMenus;
import static military.menu.review.repository.MenuUtils.pressLike;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@DataJpaTest
@ActiveProfiles("test")
public class RankRepositoryTest {
    @Autowired
    private RankRepository rankRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void shouldFindByWeekOrderByRankLimit() {
        String[] names = {"김밥", "라면", "밥", "된장찌개", "김치"};
        int[] like = {10, 9, 7, 11, 9};

        List<Menu> menus = createMenus(names);
        pressLike(menus, like);
        saveMenus(menus);

        Week week1 = new Week(2021, 6, 1);
        Week week2 = new Week(2021, 6, 2);
        Rank[] ranks = new Rank[5];

        int rank = 1;
        for(int i = 0; i < 3; i++) {
            ranks[i] = Rank.of(week1, menus.get(i), rank++);
        }

        rank = 1;
        for(int i = 3; i < 5; i++) {
            ranks[i] = Rank.of(week2, menus.get(i), rank++);
        }

        saveRanks(ranks);

        em.flush();
        em.clear();

        List<Rank> ranks1 = rankRepository.findByWeekOrderByRankLimit(week1, 2);
        List<Rank> ranks2 = rankRepository.findByWeekOrderByRankLimit(week2, 1);

        assertThat(ranks1, is(Arrays.asList(ranks[0], ranks[1])));
        assertThat(ranks2, is(Arrays.asList(ranks[3])));
    }

    private void saveMenus(List<Menu> menus) {
        menus.stream().forEach(menuRepository::save);
    }

    private void saveRanks(Rank[] ranks) {
        rankRepository.saveAll(Arrays.asList(ranks));
    }
}
