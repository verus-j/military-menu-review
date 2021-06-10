package military.menu.review.service.mnd;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.mnd.filter.MndRestProcessFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MndService {
    private final MndRestProcessFilter chain;

    public void saveFromApi() {
        chain.execute();
    }
}
