package military.menu.review.service;

import military.menu.review.mndapi.MndService;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.DailyMenuType;
import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import military.menu.review.repository.DailyMenuRepository;
import military.menu.review.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DailyMenuService {
    private MenuRepository menuRepository;
    private DailyMenuRepository dailyMenuRepository;

    @Autowired
    public DailyMenuService(MenuRepository menuRepository, DailyMenuRepository dailyMenuRepository) {
        this.menuRepository = menuRepository;
        this.dailyMenuRepository = dailyMenuRepository;
    }

    public MenuList findByDateAndType(LocalDate date, DailyMenuType type) {
        DailyMenu menu = dailyMenuRepository.findByDate(date);
        return type.menuList(menu);
    }

    public List<DailyMenu> findFromTo(LocalDate from, LocalDate to) {
        return dailyMenuRepository.findByDateBetweenOrderByDateAsc(from, to);
    }

    public void saveFromMnd(MndService service) {
        saveMenuList(service);
        saveDailyMenuList(service);
    }

    private void saveMenuList(MndService service) {
        service.findMenuList().stream()
            .filter(menu -> menuRepository.findByName(menu.getName()) == null)
            .forEach(menuRepository::save);
    }

    private void saveDailyMenuList(MndService service) {
        service.findDailyMenuList().stream()
            .filter(dailyMenu -> dailyMenuRepository.findByDate(dailyMenu.getDate()) == null)
            .forEach(this::saveDailyMenu);
    }

    private void saveDailyMenu(DailyMenu menu) {
        DailyMenu newDailyMenu = new DailyMenu(menu.getDate());
        addMenus(menu, newDailyMenu);
        dailyMenuRepository.save(newDailyMenu);
    }

    private void addMenus(DailyMenu from, DailyMenu to) {
        for(Menu menu : from.getBreakfast()) {
            Menu foundMenu = menuRepository.findByName(menu.getName());
            to.addBreakfastMenu(foundMenu);
        }

        for(Menu menu : from.getLunch()) {
            Menu foundMenu = menuRepository.findByName(menu.getName());
            to.addLunchMenu(foundMenu);
        }

        for(Menu menu : from.getDinner()) {
            Menu foundMenu = menuRepository.findByName(menu.getName());
            to.addDinnerMenu(foundMenu);
        }
    }
}
