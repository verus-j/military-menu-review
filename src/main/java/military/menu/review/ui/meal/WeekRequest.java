package military.menu.review.ui.meal;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class WeekRequest {
    @Min(2019) @Max(2099)
    private int year;
    @Min(1) @Max(12)
    private int month;
    @Min(1) @Max(6)
    private int week;

    public LocalDate firstDate() {
        return firstMonday(year, month).plusWeeks(week - 1);
    }

    public LocalDate lastDate() {
        return firstDate().plusDays(6);
    }

    public WeekRequest prevWeek() {
        return WeekRequest.from(firstDate().minusWeeks(1));
    }

    public WeekRequest nextWeek() {
        return WeekRequest.from(firstDate().plusWeeks(1));
    }

    public static WeekRequest from(LocalDate now) {
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

        return new WeekRequest(year, month, week);
    }

    public static WeekRequest from(int year, int month, int week) {
        return new WeekRequest(year, month, week);
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
