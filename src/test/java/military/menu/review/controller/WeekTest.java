package military.menu.review.controller;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class WeekTest {
    @Test
    public void shouldFirstMondayOfMonthIsWeekFirstStart() {
        LocalDate date = LocalDate.of(2021, 6, 7);
        Week week = new Week(date);

        assertThat(week.month(), is(6));
        assertThat(week.week(), is(1));
    }

    @Test
    public void shouldEveryWeekStartMonday() {
         LocalDate date = LocalDate.of(2021, 6, 14);
         Week week = new Week(date);

         assertThat(week.month(), is(6));
         assertThat(week.week(), is(2));
    }

    @Test
    public void shouldCalculateWeekBaseOnMonday() {
        LocalDate date = LocalDate.of(2021, 6, 23);
        Week week = new Week(date);

        assertThat(week.month(), is(6));
        assertThat(week.week(), is(3));
    }

    @Test
    public void shouldGetLastWeekOfPreviousMonthWhenDateIsBeforeFirstMonday() {
        LocalDate date = LocalDate.of(2021, 6, 4);
        Week week = new Week(date);

        assertThat(week.month(), is(5));
        assertThat(week.week(), is(5));
    }

    @Test
    public void shouldGetLastWeekOfYearWhenDateIsBeforeFirstMondayOfYear() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        Week week = new Week(date);

        assertThat(week.year(), is(2020));
        assertThat(week.month(), is(12));
        assertThat(week.week(), is(4));
    }
}
