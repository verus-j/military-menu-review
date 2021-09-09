package military.menu.review.ui.meal;

import military.menu.review.ui.meal.WeekRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WeekRequestTest {
    @Test
    @DisplayName("매월 첫 월요일은 첫 주")
    public void shouldFirstMondayOfMonthIsWeekFirstStart() {
        LocalDate date = LocalDate.of(2021, 6, 7);
        WeekRequest weekRequest = WeekRequest.from(date);

        assertThat(weekRequest.getMonth(), is(6));
        assertThat(weekRequest.getWeek(), is(1));
        assertThat(weekRequest.firstDate(), is(LocalDate.of(2021, 6, 7)));
        assertThat(weekRequest.lastDate(), is(LocalDate.of(2021, 6, 13)));
    }

    @Test
    @DisplayName("월요일은 주의 시작")
    public void shouldEveryWeekStartMonday() {
        LocalDate date = LocalDate.of(2021, 6, 14);
        WeekRequest weekRequest = WeekRequest.from(date);

        assertThat(weekRequest.getMonth(), is(6));
        assertThat(weekRequest.getWeek(), is(2));
        assertThat(weekRequest.firstDate(), is(LocalDate.of(2021, 6, 14)));
        assertThat(weekRequest.lastDate(), is(LocalDate.of(2021, 6, 20)));
    }

    @Test
    @DisplayName("월요일을 기준으로 주를 계산")
    public void shouldCalculateWeekBaseOnMonday() {
        LocalDate date = LocalDate.of(2021, 6, 23);
        WeekRequest weekRequest = WeekRequest.from(date);

        assertThat(weekRequest.getMonth(), is(6));
        assertThat(weekRequest.getWeek(), is(3));
        assertThat(weekRequest.firstDate(), is(LocalDate.of(2021, 6, 21)));
        assertThat(weekRequest.lastDate(), is(LocalDate.of(2021, 6, 27)));
    }

    @Test
    @DisplayName("월의 첫번째 월요일 이전의 경우에는 전달의 마지막 주에 속함")
    public void shouldGetLastWeekOfPreviousMonthWhenDateIsBeforeFirstMonday() {
        LocalDate date = LocalDate.of(2021, 6, 4);
        WeekRequest weekRequest = WeekRequest.from(date);

        assertThat(weekRequest.getMonth(), is(5));
        assertThat(weekRequest.getWeek(), is(5));
        assertThat(weekRequest.firstDate(), is(LocalDate.of(2021, 5, 31)));
        assertThat(weekRequest.lastDate(), is(LocalDate.of(2021, 6, 6)));
    }

    @Test
    @DisplayName("년의 첫번째 월요일 전의 날짜는 전년도 마지막 주에 속함")
    public void shouldGetLastWeekOfYearWhenDateIsBeforeFirstMondayOfYear() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        WeekRequest weekRequest = WeekRequest.from(date);

        assertThat(weekRequest.getYear(), is(2020));
        assertThat(weekRequest.getMonth(), is(12));
        assertThat(weekRequest.getWeek(), is(4));
        assertThat(weekRequest.firstDate(), is(LocalDate.of(2020, 12, 28)));
        assertThat(weekRequest.lastDate(), is(LocalDate.of(2021, 1, 3)));
    }

    @Test
    @DisplayName("매주의 시작 날짜 계산")
    void shouldFindFirstDayByWeek() {
        WeekRequest weekRequest = WeekRequest.from(2021, 6, 1);

        assertThat(weekRequest.getYear(), is(2021));
        assertThat(weekRequest.getMonth(), is(6));
        assertThat(weekRequest.firstDate(), is(LocalDate.of(2021, 6, 7)));
        assertThat(weekRequest.lastDate(), is(LocalDate.of(2021, 6, 13)));
    }
}
