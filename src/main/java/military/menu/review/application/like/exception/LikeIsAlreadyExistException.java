package military.menu.review.application.like.exception;

public class LikeIsAlreadyExistException extends IllegalArgumentException {
    public LikeIsAlreadyExistException(Long memberId, Long menuId) {
        super(String.format("사용자[%d]가 좋아요를 누른 메뉴[%d]는 이미 존재합니다.", memberId, menuId));
    }
}
