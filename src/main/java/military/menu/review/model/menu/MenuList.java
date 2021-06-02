package military.menu.review.model.menu;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
public class MenuList implements Iterable<Menu>{
    @Id @GeneratedValue @Column(name="MENULIST_ID")
    private Long id;

    @JsonProperty("list")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="MENULIST_MENU",
            joinColumns = @JoinColumn(name="MENULIST_ID"),
            inverseJoinColumns = @JoinColumn(name="MENU_ID")
    )
    @OrderColumn(name="POSITION")
    private List<Menu> menuList;

    @ManyToOne
    @JoinColumn(name="DAILYMENU_ID")
    private DailyMenu dailyMenu;

    @OneToMany(mappedBy="menuList")
    private List<Review> reviews;

    public MenuList() {
        this.menuList = new ArrayList<>();
    }

    public MenuList(DailyMenu dailyMenu) {
        this();
        this.dailyMenu = dailyMenu;
    }

    public long getId() {
        return id;
    }

    public void add(Menu menu) {
        menuList.add(menu);
    }

    public int size() {
        return menuList.size();
    }

    public double getKcal() {
        return menuList.stream().mapToDouble(Menu::getKcal).sum();
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
        return Objects.equals(menuList, menuList1.menuList) && Objects.equals(id, menuList1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuList, id);
    }

    @Override
    public String toString() {
        return "MenuList{" +
                "id=" + id +
                ", menuList=" + menuList +
                '}';
    }

    @Override
    public Iterator<Menu> iterator() {
        return menuList.iterator();
    }
}
