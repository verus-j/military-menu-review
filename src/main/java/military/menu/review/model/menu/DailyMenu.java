package military.menu.review.model.menu;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class DailyMenu {
    @Id @GeneratedValue @Column(name="DAILYMENU_ID")
    private Long id;

    private LocalDate date;

    @OneToMany(mappedBy = "dailyMenu", cascade = CascadeType.ALL)
    @MapKeyClass(DailyMenuType.class)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="TYPE")
    private Map<DailyMenuType, MenuList> map;

    public DailyMenu() {
    }

    public DailyMenu(LocalDate date) {
        this.date = date;
        map = new HashMap<>();
        map.put(DailyMenuType.BREAKFAST, new MenuList(this));
        map.put(DailyMenuType.LUNCH, new MenuList(this));
        map.put(DailyMenuType.DINNER, new MenuList(this));
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public MenuList getBreakfast() {
        return map.get(DailyMenuType.BREAKFAST);
    }

    public MenuList getLunch() {
        return map.get(DailyMenuType.LUNCH);
    }

    public MenuList getDinner() {
        return map.get(DailyMenuType.DINNER);
    }

    public double getKcal() {
        return getBreakfast().getKcal() + getLunch().getKcal() + getDinner().getKcal();
    }

    public void addBreakfastMenu(Menu menu) {
        getBreakfast().add(menu);
    }

    public void addLunchMenu(Menu menu) {
        getLunch().add(menu);
    }

    public void addDinnerMenu(Menu menu) {
        getDinner().add(menu);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyMenu dailyMenu = (DailyMenu) o;
        return Objects.equals(id, dailyMenu.id) && Objects.equals(date, dailyMenu.date) && Objects.equals(map, dailyMenu.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, map);
    }

    @Override
    public String toString() {
        return "DailyMenu{" +
                "id=" + id +
                ", date=" + date +
                ", map=" + map +
                '}';
    }
}
