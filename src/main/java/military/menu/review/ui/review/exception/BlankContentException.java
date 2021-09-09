package military.menu.review.ui.review.exception;

public class BlankContentException extends IllegalArgumentException {
    public BlankContentException() {
        super("리뷰 내용이 없습니다.");
    }
}
