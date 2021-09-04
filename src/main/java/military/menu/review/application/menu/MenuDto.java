package military.menu.review.application.menu;

import java.util.Optional;

public class MenuDto {
    public String getName() {
        return null;
    }

    public double getKcal() {
        return 0;
    }

    public int getLike() {
        return 0;
    }

    public Optional<Long> getMemberId() {
        return Optional.ofNullable(1L);
    }
}
