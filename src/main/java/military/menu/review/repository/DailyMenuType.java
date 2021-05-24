package military.menu.review.repository;

public enum DailyMenuType {
    BREAKFAST(1), LUNCH(2), DINNER(3);

    private int value;

    DailyMenuType(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }

    public DailyMenuType valueOf(int value) {
        switch(value) {
            case 1:
                return BREAKFAST;
            case 2:
                return LUNCH;
            case 3:
                return DINNER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
