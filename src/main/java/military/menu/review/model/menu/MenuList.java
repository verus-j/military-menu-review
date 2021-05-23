package military.menu.review.model.menu;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuList {
    private List<Menu> menuList;

    public MenuList() {
        menuList = new ArrayList<>();
    }

    public void addMenu(Menu menu) {
        menuList.add(menu);
    }

    public double getTotalCalorie() {
        return menuList.stream()
                .map(Menu::getCalorie)
                .reduce(0.0, (acc, value) -> acc + value);
    }

    @Override
    public String toString() {
        return "MenuList{" +
                "menuList=" + menuList +
                '}';
    }
}
