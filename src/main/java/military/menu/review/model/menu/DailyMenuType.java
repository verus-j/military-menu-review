package military.menu.review.model.menu;

public enum DailyMenuType {
    BREAKFAST, LUNCH, DINNER;

    public MenuList menuList(DailyMenu menu) {
        switch(this) {
            case BREAKFAST:
                return menu.getBreakfast();
            case LUNCH:
                return menu.getLunch();
            case DINNER:
                return menu.getDinner();
            default:
                throw new IllegalArgumentException();
        }
    }
}
