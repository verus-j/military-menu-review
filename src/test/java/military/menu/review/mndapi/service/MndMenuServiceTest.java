package military.menu.review.mndapi.service;

import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import military.menu.review.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MndMenuServiceTest {
    MndApi api;
    MenuRepository repository;
    MndMenuService service;

    @BeforeEach
    void setUp() {
        api = mock(MndApi.class);
        repository = mock(MenuRepository.class);

        service = new MndMenuService();
        service.setApi(api);
        service.setRepository(repository);
    }

    @Test
    public void shouldSaveMndMenuListUsingMenuListRepository() {
        when(api.parse(any(MenuListParser.class))).thenReturn(menuList());
        service.saveAllMenuList();
        verify(repository).insertAll(menuList());
    }

    private MenuList menuList() {
        MenuList menuList = new MenuList();
        menuList.add(Menu.of("밥", 0.0));
        menuList.add(Menu.of("김치", 0.0));
        return menuList;
    }
}
