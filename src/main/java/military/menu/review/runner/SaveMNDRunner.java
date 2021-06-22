package military.menu.review.runner;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.mnd.MndService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(1)
public class SaveMNDRunner implements ApplicationRunner {
    private final MndService service;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
//        service.saveFromApi();
    }
}
