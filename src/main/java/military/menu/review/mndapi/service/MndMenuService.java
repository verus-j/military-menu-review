package military.menu.review.mndapi.service;

import military.menu.review.mndapi.MndApi;
import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.mndapi.parser.MenuTableParser;
import military.menu.review.model.menu.MenuList;
import military.menu.review.model.menu.MenuTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class MndMenuService {
    @Value("${mnd.baseUrl}")
    private String baseUrl;
    private MndApi api;

    public MndMenuService() {
        api = new MndApi();
    }

    @PostConstruct
    public void setBaseUrl() {
        api.setBaseUrl(baseUrl);
    }

    public void setTemplate(RestTemplate template) {
        api.setTemplate(template);
    }

    public MenuTable menuTable() {
        return api.parse(new MenuTableParser());
    }

    public MenuList menuList() {
        return api.parse(new MenuListParser());
    }
}
