package military.menu.review.infra.meal;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.meal.MealDao;
import military.menu.review.domain.meal.MealDto;
import military.menu.review.domain.meal.MealType;
import military.menu.review.domain.member.Member;
import military.menu.review.domain.menu.MenuDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MealDaoImpl implements MealDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MealDto> selectByDateBetweenWithIsLiked(LocalDate start, LocalDate end, Member member) {
        String sql = "select *, case when i.member_id = ? then true else false end is_liked " +
                "from meal join selected_menu on selected_menu.meal_id = meal.meal_id " +
                "join menu on selected_menu.menu_id = menu.menu_id " +
                "left join (select * from likes where likes.member_id = ?) as i on menu.menu_id = i.menu_id " +
                "where meal.date between ? and ? order by meal.date asc, meal.meal_type asc, menu.name asc";

        Long memberId = member != null ? member.getId() : null;
        boolean isLogin = member != null;
        MealDtoRowMapper rowMapper = new MealDtoRowMapper(isLogin);
        return jdbcTemplate.query(sql, rowMapper, memberId, memberId, start, end);
    }

    @Override
    public MealDto selectByIdWithIsLiked(Long id, Member member) {
        String sql = "select *, case when i.member_id = ? then true else false end is_liked " +
                "from meal join selected_menu on selected_menu.meal_id = meal.meal_id " +
                "join menu on selected_menu.menu_id = menu.menu_id " +
                "left join (select * from likes where likes.member_id = ?) as i on menu.menu_id = i.menu_id " +
                "where meal.meal_id = ? order by menu.name asc";

        Long memberId = member != null ? member.getId() : null;
        boolean isLogin = member != null;
        MealDtoRowMapper rowMapper = new MealDtoRowMapper(isLogin);
        List<MealDto> result = jdbcTemplate.query(sql, rowMapper, memberId, memberId, id);
        return result.size() > 0 ? result.get(0) : null;
    }
}
