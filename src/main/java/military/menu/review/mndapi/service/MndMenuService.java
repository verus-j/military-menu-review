package military.menu.review.mndapi.service;

import lombok.extern.slf4j.Slf4j;
import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.mndapi.parser.MenuTableParser;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.repository.MenuRepository;
import military.menu.review.repository.DailyMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class MndMenuService {
    @Value("${mnd.baseUrl}")
    private String baseUrl;
    private MndApi api;
    private MenuRepository menuRepository;
    private DailyMenuRepository dailyMenuRepository;

    public MndMenuService() {
        api = new MndApi();
    }

    @PostConstruct
    public void setBaseUrl() {
        api.setBaseUrl(baseUrl);
    }

    public void setApi(MndApi api) {
        this.api = api;
    }

    @Autowired
    public void setMenuRepository(MenuRepository repository) {
        this.menuRepository = repository;
    }

    @Autowired
    public void setDailyMenuRepository(DailyMenuRepository dailyMenuRepository) {
        this.dailyMenuRepository = dailyMenuRepository;
    }

    @Transactional
    public void save() {
        menuList().forEach(this::saveMenu);
        menuTable().forEach(this::saveMenuTable);
    }

    private Stream<Menu> menuList() {
        return api.parse(new MenuListParser()).getList().stream();
    }

    private void saveMenu(Menu menu) {
        Optional<Menu> menuOptional = menuRepository.find(menu.getName());
        menuOptional.ifPresentOrElse(
            m -> {},
            () -> menuRepository.insert(menu)
        );
    }

    private Stream<DailyMenu> menuTable() {
        return api.parse(new MenuTableParser()).getTable().stream();
    }

    private void saveMenuTable(DailyMenu dailyMenu) {
        Optional<DailyMenu> dailyMenuOptional = dailyMenuRepository.find(dailyMenu.getDate());
        dailyMenuOptional.ifPresentOrElse(
            m -> {},
            () -> {
                dailyMenuRepository.insert(dailyMenu);
            }
        );
    }
}
