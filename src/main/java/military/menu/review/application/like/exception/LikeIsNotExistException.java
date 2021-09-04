package military.menu.review.application.like.exception;

public class LikeIsNotExistException extends IllegalArgumentException {
    public LikeIsNotExistException(Long memberId, Long menuId) {
        super(String.format("사용자[%d]는 메뉴[%d]에 대해 좋아요를 누르지 않았습니다.", memberId, menuId));
    }
}
