package military.menu.review.model.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@DisplayName("메뉴 리스트 테스트")
public class MenuListTest {
    @Test
    @DisplayName("메뉴 리스트의 전체 칼로리 계산")
    public void shouldCalculateTotalCalorie() {
        MenuList list = new MenuList();
        list.add(Menu.of("밥", 100.0));
        list.add(Menu.of("김치", 10.0));
        list.add(Menu.of("라면", 23.0));
        assertThat(list.getTotalCalorie(), is(133.0));
    }
}
