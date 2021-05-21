package military.menu.review;

import military.menu.review.model.Menu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class MndApi {
    @Value("${mnd.baseUrl}")
    private String baseUrl;
    private RestTemplate template;
    private MndApiParser parser;

    public MndApi() {
        template = new RestTemplate();
        parser = new MndApiParser();
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public int getTotalCount() {
        return parser.totalCount(request(1));
    }

    public List<Menu> getMenuList() {
        return parser.menuList(request(getTotalCount()));
    }

    private String request(int endIndex) {
        return template.getForObject(String.format("%s/1/%d", baseUrl, endIndex), String.class);
    }
}
