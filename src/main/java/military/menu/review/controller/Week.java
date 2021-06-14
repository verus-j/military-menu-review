package military.menu.review.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Week {
    private LocalDate now;
    private int month;
    private int week;
    private int year;

    public Week(LocalDate now) {
        this.now = now;
        initMonthAndWeek();
    }

    private void initMonthAndWeek() {
        LocalDate firstMonday = firstMonday(now.getYear(), now.getMonthValue());

        if(now.getDayOfMonth() < firstMonday.getDayOfMonth()) {
            this.year = now.minusMonths(1).getYear();
            this.month = now.minusMonths(1).getMonthValue();
            this.week = lastWeekOfPreviousMonth();
        } else {
            this.year = now.getYear();
            this.month = now.getMonthValue();
            this.week = ((now.getDayOfMonth() - firstMonday.getDayOfMonth()) / 7) + 1;
        }
    }

    private LocalDate firstMonday(int year, int month) {
        LocalDate firstDate = LocalDate.of(year, month, 1);
        return firstDate.plusDays(adjustPlusDay(firstDate.getDayOfWeek()));
    }

    private int lastWeekOfPreviousMonth() {
        LocalDate firstDate = LocalDate.of(now.getYear(), now.getMonth(), 1).minusMonths(1);
        LocalDate firstMonday = firstMonday(firstDate.getYear(), firstDate.getMonthValue());
        LocalDate lastMonday = lastMonday(firstDate);
        return ((lastMonday.getDayOfMonth() - firstMonday.getDayOfMonth()) / 7) + 1;
    }

    private LocalDate lastMonday(LocalDate firstDate) {
        LocalDate lastDate = LocalDate.of(firstDate.getYear(), firstDate.getMonth(), firstDate.lengthOfMonth());
        return lastDate.minusDays(adjustMinusDay(lastDate.getDayOfWeek()));
    }

    private long adjustPlusDay(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY ? 0 : (8 - dayOfWeek.getValue());
    }

    private long adjustMinusDay(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY ? 0 : dayOfWeek.getValue() - 1;
    }

    public int month() {
        return month;
    }

    public int week() {
        return week;
    }

    public int year() {
        return year;
    }
}
