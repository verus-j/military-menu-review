package military.menu.review.domain.menu;

import military.menu.review.application.menu.MenuDto;

public interface MenuDao {
    MenuDto selectByIdWithIsLiked(Long menuId, Long MemberId);
}
