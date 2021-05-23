package military.menu.review.mndapi.service;

import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import military.menu.review.repository.MenuListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

public class MndMenuServiceTest {
    MndApi api;
    MenuListRepository repository;
    MndMenuService service;

    @BeforeEach
    void setUp() {
        api = mock(MndApi.class);
        repository = mock(MenuListRepository.class);

        service = new MndMenuService();
        service.setApi(api);
        service.setRepository(repository);
    }

    @Test
    public void shouldSaveMndMenuListUsingMenuListRepository() {
        when(api.parse(any(MenuListParser.class))).thenReturn(menuList());
        service.saveAllMenuList();
        verify(repository).insert(menuList());
    }

    private MenuList menuList() {
        MenuList menuList = new MenuList();
        menuList.add(Menu.of("밥", 0.0));
        menuList.add(Menu.of("김치", 0.0));
        return menuList;
    }
}
