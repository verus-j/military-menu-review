package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MenuRepository {
    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public void deleteAll() {
        template.update("delete from Menu");
    }

    public Optional<Menu> find(String name) {
        try {
            Menu menu = template.queryForObject("select * from Menu where name=?",
                    BeanPropertyRowMapper.newInstance(Menu.class), name);
            return Optional.of(menu);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public void insert(Menu menu) {
        template.update("insert into Menu(name, calorie, totalLike) values(?, ?, ?)",
            menu.getName(), menu.getCalorie(), menu.getTotalLike());
    }
}
