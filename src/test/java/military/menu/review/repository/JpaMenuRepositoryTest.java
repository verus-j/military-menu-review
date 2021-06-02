package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class JpaMenuRepositoryTest {
    @Autowired
    MenuRepository repository;

    @Test
    public void shouldSave() {
        Menu m = repository.save(Menu.of("밥", 123.0));
        Optional<Menu> optional = repository.findById(m.getId());

        optional.ifPresentOrElse(menu -> {
            assertThat(menu.getName(), is("밥"));
            assertThat(menu.getKcal(), is(123.0));
        }, () -> {
            fail();
        });
    }

    @Test
    public void shouldFindByName() {
        Menu actual = repository.save(Menu.of("밥", 111.0));
        Menu expect = repository.findByName("밥");

        assertThat(expect, is(actual));
    }
}
