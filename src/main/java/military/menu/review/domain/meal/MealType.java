package military.menu.review.domain;

public enum MealType {
    BREAKFAST(0), LUNCH(1), DINNER(2);
    private int value;

    MealType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
