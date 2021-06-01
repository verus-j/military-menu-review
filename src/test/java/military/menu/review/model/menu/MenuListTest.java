package military.menu.review.model.menu;

import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class MenuListTest {
    MenuList menuList;

    @BeforeEach
    void setUp() {
        menuList = new MenuList();
        menuList.add(new Menu("밥", 104.0));
        menuList.add(new Menu("김치", 12.3));
    }

    @Test
    public void shouldAddMenu() {
        assertThat(menuList.size(), is(2));
        assertThat(menuList.get(0), is(new Menu("밥", 104.0)));
        assertThat(menuList.get(1), is(new Menu("김치", 12.3)));
    }
}
