package military.menu.review.controller;

import military.menu.review.mndapi.MndApi;
import military.menu.review.mndapi.parser.MenuTableParser;
import military.menu.review.model.MenuTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MilitaryMenuController {
    MndApi api;

    @Autowired
    public void setMndApi(MndApi api) {
        this.api = api;
    }

    @GetMapping("/menuTable")
    public MenuTable menuTable() {
        return api.parse(new MenuTableParser());
    }
}
