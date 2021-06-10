package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.Like;
import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.repository.LikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;

    public Like like(Member member, Menu menu) {
        return likeRepository.save(Like.of(member, menu));
    }
}
