package military.menu.review.mndapi;

import military.menu.review.mndapi.parser.MndApiDataParser;
import military.menu.review.mndapi.parser.TotalCountParser;
import org.springframework.web.client.RestTemplate;

public class MndApi {
    private String baseUrl;
    private RestTemplate template;

    public MndApi() {
        template = new RestTemplate();
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public <T> T parse(MndApiDataParser<T> parser) {
        return parser.parse(request(getTotalCount()));
    }

    private int getTotalCount() {
        return new TotalCountParser().parse(request(1));
    }

    private String request(int endIndex) {
        return template.getForObject(String.format("%s/1/%d", baseUrl, endIndex), String.class);
    }
}
