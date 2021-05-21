package military.menu.review;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import military.menu.review.model.Menu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.instrument.IllegalClassFormatException;
import java.util.List;
import java.util.Map;

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
}
