package military.menu.review.domain;

import lombok.ToString;

import javax.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Embeddable
@ToString
public class Week {
    private int month;
    private int week;
    private int year;

    protected Week() {}

    public Week(int year, int month, int week) {
        this.year = year;
        this.month = month;
        this.week = week;
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

    public LocalDate firstDate() {
        LocalDate firstDate = firstMonday(year, month).plusWeeks(week - 1);

        if(firstDate.getMonthValue() != month){
            throw new IllegalArgumentException();
        }

        return firstDate;
    }

    public LocalDate lastDate() {
        return firstDate().plusDays(6);
    }

    public static Week from(LocalDate now) {
        LocalDate firstMonday = firstMonday(now.getYear(), now.getMonthValue());
        int year;
        int month;
        int week;

        if(now.getDayOfMonth() < firstMonday.getDayOfMonth()) {
            year = now.minusMonths(1).getYear();
            month = now.minusMonths(1).getMonthValue();
            week = lastWeekOfPreviousMonth(now);
        } else {
            year = now.getYear();
            month = now.getMonthValue();
            week = ((now.getDayOfMonth() - firstMonday.getDayOfMonth()) / 7) + 1;
        }

        return new Week(year, month, week);
    }

    private static LocalDate firstMonday(int year, int month) {
        LocalDate firstDate = LocalDate.of(year, month, 1);
        return firstDate.plusDays(adjustPlusDay(firstDate.getDayOfWeek()));
    }

    private static long adjustPlusDay(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY ? 0 : (8 - dayOfWeek.getValue());
    }

    private static int lastWeekOfPreviousMonth(LocalDate now) {
        LocalDate firstDate = LocalDate.of(now.getYear(), now.getMonth(), 1).minusMonths(1);
        LocalDate firstMonday = firstMonday(firstDate.getYear(), firstDate.getMonthValue());
        LocalDate lastMonday = lastMonday(firstDate);
        return ((lastMonday.getDayOfMonth() - firstMonday.getDayOfMonth()) / 7) + 1;
    }

    private static LocalDate lastMonday(LocalDate firstDate) {
        LocalDate lastDate = LocalDate.of(firstDate.getYear(), firstDate.getMonth(), firstDate.lengthOfMonth());
        return lastDate.minusDays(adjustMinusDay(lastDate.getDayOfWeek()));
    }

    private static long adjustMinusDay(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.MONDAY ? 0 : dayOfWeek.getValue() - 1;
    }
}
