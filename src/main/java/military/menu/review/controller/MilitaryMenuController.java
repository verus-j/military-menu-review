package military.menu.review.controller;

import military.menu.review.mndapi.service.MndMenuService;
import military.menu.review.model.menu.MenuTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilitaryMenuController {
    MndMenuService service;

    @Autowired
    public void setMndApi(MndMenuService service) {
        this.service = service;
    }

    @GetMapping("/menuTable")
    public MenuTable menuTable() {
        return service.menuTable();
    }
}
