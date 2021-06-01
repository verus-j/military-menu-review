package military.menu.review.model.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuList {
    private List<Menu> menuList;

    public MenuList() {
        this.menuList = new ArrayList<>();
    }

    public void add(Menu menu) {
        menuList.add(menu);
    }

    public int size() {
        return menuList.size();
    }

    public Menu get(int index) {
        return menuList.get(index);
    }

    public static MenuList asList(Menu... menus) {
        MenuList list = new MenuList();
        for(Menu menu : menus) {
            list.add(menu);
        }
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuList menuList1 = (MenuList) o;
        return Objects.equals(menuList, menuList1.menuList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuList);
    }
}
