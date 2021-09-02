package military.menu.review.repository;

import military.menu.review.domain.menu.Menu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuUtils {
    public static List<Menu> createMenus(String[] names) {
        return Arrays.stream(names).map(n -> Menu.of(n, 111.1)).collect(Collectors.toList());
    }

    public static void pressLike(List<Menu> menus, int[] like){
        int index = 0;
        for(Menu m : menus) {
            for(int i = 0; i < like[index]; i++) {
                m.like();
            }
            index++;
        }
    }
}
