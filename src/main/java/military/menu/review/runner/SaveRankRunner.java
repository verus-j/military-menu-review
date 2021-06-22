package military.menu.review.runner;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Menu;
import military.menu.review.domain.Rank;
import military.menu.review.domain.Week;
import military.menu.review.repository.MenuRepository;
import military.menu.review.repository.RankRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Order(2)
public class SaveRankRunner implements ApplicationRunner {
    private final MenuRepository menuRepository;
    private final RankRepository rankRepository;
    private String[] names = {"밥", "돈가스", "가지볶음", "잡채밥", "계란말이", "카레밥", "자장밥", "쇠고기", "돼지갈비찜", "닭갈비", "팥빙수"};
    private int[] likes = {122, 114, 105, 92, 85, 40, 31, 28, 15, 15, 15};
    private int[] ranks = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9};

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
//        for(int i = 0; i < names.length; i++) {
//            Menu menu = menuRepository.findByName(names[i]);
//            Rank rank = Rank.of(new Week(2021, 6, 2), menu, likes[i], ranks[i]);
//            rankRepository.save(rank);
//        }
    }
}
