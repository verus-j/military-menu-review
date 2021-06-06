package military.menu.review.mnd.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.mnd.chain.MndSaveHeaderChain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MndService {
    private final MndSaveHeaderChain chain;

    public void saveFromApi() {
        chain.execute();
    }
}
