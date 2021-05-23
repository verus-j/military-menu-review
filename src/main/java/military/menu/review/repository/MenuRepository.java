package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Optional;

@Repository
public class MenuRepository {
    private JdbcTemplate template;

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Transactional
    public void insertAll(MenuList menuList) {
        for(Menu menu : menuList.getList()) {
            template.update("insert into Menu(name, calorie, totalLike) values(?, ?, ?)",
                    menu.getName(), menu.getCalorie(), menu.getTotalLike());
        }
    }

    public MenuList findAll() {
        MenuList menuList = new MenuList();
        template.query("select * from Menu", BeanPropertyRowMapper.newInstance(Menu.class))
                .stream().forEach(menuList::add);
        return menuList;
    }

    public void deleteAll() {
        template.update("delete from Menu");
    }
}
