package military.menu.review.infra.menu;

import lombok.RequiredArgsConstructor;
import military.menu.review.application.menu.MenuDto;
import military.menu.review.domain.menu.MenuDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class MenuDaoImpl implements MenuDao {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<MenuDto> rowMapper = new RowMapper<MenuDto>() {
        @Override
        public MenuDto mapRow(ResultSet rs, int i) throws SQLException {
            Long id = rs.getLong("menu_id");
            Double kcal = rs.getDouble("kcal");
            String name = rs.getString("name");
            Long like = rs.getLong("likes");
            boolean isLiked = rs.getBoolean("is_liked");

            return MenuDto.builder()
                    .id(id)
                    .kcal(kcal)
                    .name(name)
                    .like(like)
                    .isLiked(isLiked)
                    .build();
        }
    };

    @Override
    public MenuDto selectByIdWithIsLiked(Long menuId, Long memberId) {
        String sql = "select menu.menu_id, menu.kcal, menu.name, menu.likes, " +
                "case when i.member_id = ? then true else false end is_liked " +
                "from menu left join (select * from likes where likes.member_id = ?) as i on menu.menu_id = i.menu_id " +
                "where menu.menu_id=?";

        return jdbcTemplate.queryForObject(sql, rowMapper, memberId, memberId, menuId);
    }
}
