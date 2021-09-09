package military.menu.review.infra.menu;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.menu.MenuDto;
import military.menu.review.domain.menu.MenuDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MenuDaoImpl implements MenuDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<MenuDto> selectByIdWithIsLiked(Long menuId, Long memberId) {
        String sql = "select menu.menu_id, menu.kcal, menu.name, menu.likes, " +
                "case when i.member_id = ? then true else false end is_liked " +
                "from menu left join (select * from likes where likes.member_id = ?) as i on menu.menu_id = i.menu_id " +
                "where menu.menu_id=?";
        boolean isLogin = memberId != null;
        List<MenuDto> list = jdbcTemplate.query(sql, rowMapper(isLogin), memberId, memberId, menuId);
        return list.size() == 0 ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<MenuDto> selectAllWithIsLiked(Pageable pageable, Long memberId) {
        String sql = "select menu.menu_id, menu.kcal, menu.name, menu.likes, " +
                "case when i.member_id = ? then true else false end is_liked " +
                "from menu left join (select * from likes where likes.member_id = ? ) as i on menu.menu_id = i.menu_id ";
        boolean isLogin = memberId != null;
        return jdbcTemplate.query(sql + pagingSql(pageable), rowMapper(isLogin), memberId, memberId);
    }

    private String pagingSql(Pageable pageable) {
        return String.format("%s %s %s", orderBy(pageable), limit(pageable), offset(pageable));
    }

    private String limit(Pageable pageable) {
        return String.format("limit %d", pageable.getPageSize());
    }

    private String offset(Pageable pageable) {
        return String.format("offset %d", pageable.getOffset());
    }

    private String orderBy(Pageable pageable) {
        StringBuilder builder = new StringBuilder("order by");
        Sort sort = pageable.getSort();
        for(Sort.Order order : sort) {
            builder.append(" ").append(order.getProperty());
            builder.append(" ").append(order.getDirection().name());
        }
        return builder.toString();
    }

    private RowMapper rowMapper(boolean isLogin) {
        return (rs, i) -> {
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
                    .isLogin(isLogin)
                    .build();
        };
    }
}
