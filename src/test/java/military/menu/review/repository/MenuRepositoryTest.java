package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@SpringBootTest
public class MenuRepositoryTest {
    @Autowired
    MenuRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void shouldSaveMenuList() {
        repository.insertAll(menuList());
        assertThat(repository.findAll(), is(menuList()));
    }

    @Test
    public void shouldSaveMenuListTransactional() {
        try {
            repository.insertAll(exceptionalMenuList());
        }catch(Exception e) { }

        assertThat(repository.findAll(), is(new MenuList()));
    }

    private MenuList exceptionalMenuList() {
        return new MenuList() {
            Menu errorMenu = new Menu() {
                public String getName() {
                    throw new RuntimeException("occur from getName()");
                }
            };

            public List<Menu> getList() {
                return Arrays.asList(Menu.of("라면", 0.0, 0), errorMenu);
            }
        };
    }

    public MenuList menuList() {
        MenuList menuList = new MenuList();
        menuList.add(Menu.of("밥", 0.0));
        menuList.add(Menu.of("김치", 0.0));
        return menuList;
    }
}
