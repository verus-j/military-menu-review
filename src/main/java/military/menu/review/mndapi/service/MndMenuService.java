package military.menu.review.mndapi.service;

import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.repository.MenuListRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MndMenuService {
    @Value("${mnd.baseUrl}")
    private String baseUrl;
    private MndApi api;
    private MenuListRepository repository;

    public MndMenuService() {
        api = new MndApi();
        repository = new MenuListRepository();
    }

    @PostConstruct
    public void setBaseUrl() {
        api.setBaseUrl(baseUrl);
    }

    public void setApi(MndApi api) {
        this.api = api;
    }

    public void setRepository(MenuListRepository repository) {
        this.repository = repository;
    }

    public void saveAllMenuList() {
        repository.insert(api.parse(new MenuListParser()));
    }
}
