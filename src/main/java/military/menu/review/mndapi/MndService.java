package military.menu.review.mndapi;

import military.menu.review.mndapi.parser.DailyMenuListParser;
import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.model.menu.DailyMenu;
import military.menu.review.model.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MndService {
    private MndApi api;

    @Autowired
    public MndService(MndApi api) {
        this.api = api;
    }

    public List<Menu> findMenuList() {
        return api.parse(new MenuListParser());
    }

    public List<DailyMenu> findDailyMenuList() {
        return api.parse(new DailyMenuListParser());
    }
}
