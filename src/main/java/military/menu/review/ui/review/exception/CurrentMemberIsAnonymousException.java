package military.menu.review.ui.review.exception;

public class CurrentMemberIsAnonymousException extends IllegalStateException {
    public CurrentMemberIsAnonymousException() {
        super("현재 접속한 사용자가 없습니다.");
    }
}
