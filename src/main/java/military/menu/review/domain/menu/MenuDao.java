package military.menu.review.domain.menu;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MenuDao {
    Optional<MenuDto> selectByIdWithIsLiked(Long menuId, Long MemberId);
    List<MenuDto> selectAllWithIsLiked(Pageable pageable, Long memberId);
}
