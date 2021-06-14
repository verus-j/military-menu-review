package military.menu.review.controller;

import military.menu.review.domain.Week;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeekTest {
    @Test
    public void shouldFirstMondayOfMonthIsWeekFirstStart() {
        LocalDate date = LocalDate.of(2021, 6, 7);
        Week week = Week.from(date);

        assertThat(week.month(), is(6));
        assertThat(week.week(), is(1));
        assertThat(week.firstDate(), is(LocalDate.of(2021, 6, 7)));
        assertThat(week.lastDate(), is(LocalDate.of(2021, 6, 13)));
    }

    @Test
    public void shouldEveryWeekStartMonday() {
        LocalDate date = LocalDate.of(2021, 6, 14);
        Week week = Week.from(date);

        assertThat(week.month(), is(6));
        assertThat(week.week(), is(2));
        assertThat(week.firstDate(), is(LocalDate.of(2021, 6, 14)));
        assertThat(week.lastDate(), is(LocalDate.of(2021, 6, 20)));
    }

    @Test
    public void shouldCalculateWeekBaseOnMonday() {
        LocalDate date = LocalDate.of(2021, 6, 23);
        Week week = Week.from(date);

        assertThat(week.month(), is(6));
        assertThat(week.week(), is(3));
        assertThat(week.firstDate(), is(LocalDate.of(2021, 6, 21)));
        assertThat(week.lastDate(), is(LocalDate.of(2021, 6, 27)));
    }

    @Test
    public void shouldGetLastWeekOfPreviousMonthWhenDateIsBeforeFirstMonday() {
        LocalDate date = LocalDate.of(2021, 6, 4);
        Week week = Week.from(date);

        assertThat(week.month(), is(5));
        assertThat(week.week(), is(5));
        assertThat(week.firstDate(), is(LocalDate.of(2021, 5, 31)));
        assertThat(week.lastDate(), is(LocalDate.of(2021, 6, 6)));
    }

    @Test
    public void shouldGetLastWeekOfYearWhenDateIsBeforeFirstMondayOfYear() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        Week week = Week.from(date);

        assertThat(week.year(), is(2020));
        assertThat(week.month(), is(12));
        assertThat(week.week(), is(4));
        assertThat(week.firstDate(), is(LocalDate.of(2020, 12, 28)));
        assertThat(week.lastDate(), is(LocalDate.of(2021, 1, 3)));
    }

    @Test
    void shouldFindFirstDayByWeek() {
        Week week = new Week(2021, 6, 1);

        assertThat(week.year(), is(2021));
        assertThat(week.month(), is(6));
        assertThat(week.firstDate(), is(LocalDate.of(2021, 6, 7)));
        assertThat(week.lastDate(), is(LocalDate.of(2021, 6, 13)));
    }

    @Test
    void shouldThrowExceptionNotProperWeeks() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Week(2021, 6, 5).firstDate();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Week(2021, 6, 5).lastDate();
        });
    }
}
