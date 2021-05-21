package military.menu.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import military.menu.review.model.Menu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MilitaryMenuApi {
    @Value("${mnd.baseUrl}")
    private String baseUrl;
    private RestTemplate template;
    private ObjectMapper mapper;

    public MilitaryMenuApi() {
        mapper = new ObjectMapper();
        template = new RestTemplate();
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public int getTotalCount() {
        try {
            String json = template.getForObject(baseUrl + "/1/1", String.class);
            Map<String, Object> map = mapper.readValue(json, Map.class);
            map = (Map)map.get("DS_TB_MNDT_DATEBYMLSVC_ATC");
            return (int)map.get("list_total_count");
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Menu> getMenuList() {
        List<Menu> menuList = new ArrayList<>();
        int totalCount = getTotalCount();

        try {
            String json = template.getForObject(baseUrl + "/1/" + totalCount, String.class);
            Map<String, Object> map = mapper.readValue(json, Map.class);
            map = (Map)map.get("DS_TB_MNDT_DATEBYMLSVC_ATC");
            List<Map<String, String>> dailyMenuTable = (List)map.get("row");

            for(Map<String, String> dailyMenu : dailyMenuTable) {
                String lunch = dailyMenu.get("lunc").replaceAll("\\(.*\\)", "");
                String lunchCalorie = dailyMenu.get("lunc_cal").replaceAll("kcal", "");
                String dinner = dailyMenu.get("dinr").replaceAll("\\(.*\\)", "");
                String dinnerCalorie = dailyMenu.get("dinr_cal").replaceAll("kcal", "");
                String breakfast = dailyMenu.get("brst").replaceAll("\\(.*\\)", "");
                String breakfastCalorie = dailyMenu.get("brst_cal").replaceAll("kcal", "");

                menuList.addAll(Arrays.asList(Menu.of(lunch, Double.parseDouble(lunchCalorie)),
                        Menu.of(dinner, Double.parseDouble(dinnerCalorie)),
                        Menu.of(breakfast, Double.parseDouble(breakfastCalorie)))
                );
            }

        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }

        return menuList;
    }
}
