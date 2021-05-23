package military.menu.review.model.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class MenuList {
    private List<Menu> menuList;

    public MenuList() {
        menuList = new ArrayList<>();
    }

    public void add(Menu menu) {
        menuList.add(menu);
    }

    public double getTotalCalorie() {
        return menuList.stream()
                .map(Menu::getCalorie)
                .reduce(0.0, (acc, value) -> acc + value);
    }

    public List<Menu> getList() {
        return menuList;
    }
}
