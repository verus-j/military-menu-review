package military.menu.review.mndapi.service;

import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.mndapi.parser.MenuTableParser;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import military.menu.review.model.menu.DailyMenuList;
import military.menu.review.repository.MenuRepository;
import military.menu.review.repository.DailyMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class MndMenuServiceTest {
    MndApi api;
    MenuRepository menuRepository;
    DailyMenuRepository dailyMenuRepository;
    MndMenuService service;

    @BeforeEach
    void setUp() {
        api = mock(MndApi.class);
        menuRepository = mock(MenuRepository.class);
        dailyMenuRepository = mock(DailyMenuRepository.class);

        service = new MndMenuService();
        service.setApi(api);
        service.setMenuRepository(menuRepository);
        service.setDailyMenuRepository(dailyMenuRepository);
    }

    @Test
    public void shouldSaveMenuListUsingMenuListRepository() {
        when(api.parse(any(MenuListParser.class))).thenReturn(menuList());
        when(api.parse(any(MenuTableParser.class))).thenReturn(menuTable());

        when(menuRepository.find("밥")).thenReturn(Optional.empty());
        when(menuRepository.find("김치")).thenReturn(Optional.empty());
        when(menuRepository.find("라면")).thenReturn(Optional.of(Menu.of("라면", 0.0)));

        when(dailyMenuRepository.find(LocalDate.of(2021, 4, 23))).thenReturn(Optional.of(new DailyMenu(LocalDate.of(2021, 4, 23))));
        when(dailyMenuRepository.find(LocalDate.of(2021, 4, 24))).thenReturn(Optional.empty());

        service.save();

        InOrder inorder = inOrder(menuRepository, dailyMenuRepository);
        menuList().getList().stream().forEach((menu) -> {
            inorder.verify(this.menuRepository).find(menu.getName());
            if(!menu.getName().equals("라면")) {
                inorder.verify(this.menuRepository).insert(menu);
            }
        });

        menuTable().getTable().stream().forEach((dailyMenu) -> {
            inorder.verify(dailyMenuRepository).find(dailyMenu.getDate());
            if(!dailyMenu.getDate().equals(LocalDate.of(2021, 4, 23))){
                dailyMenuRepository.insert(dailyMenu);
            }
        });
    }

    private MenuList menuList() {
        MenuList menuList = new MenuList();
        menuList.add(Menu.of("밥", 0.0));
        menuList.add(Menu.of("김치", 0.0));
        menuList.add(Menu.of("라면", 0.0));
        return menuList;
    }

    private DailyMenuList menuTable() {
        DailyMenuList dailyMenuList = new DailyMenuList();
        dailyMenuList.addDailyMenu(new DailyMenu(LocalDate.of(2021, 4, 23)));
        dailyMenuList.addDailyMenu(new DailyMenu(LocalDate.of(2021, 4, 24)));
        return dailyMenuList;
    }
}
