package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@Transactional
public class JpaMenuRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Resource(name="jpaMenuRepository")
    MenuRepository repository;

    @Test
    public void shouldInsert() {
        repository.insert(Menu.of("밥", 123.0));
        Menu menu = repository.findByName("밥");

        assertThat(menu.getName(), is("밥"));
        assertThat(menu.getKcal(), is(123.0));
    }

    @Test
    public void shouldFindAll() {
        repository.insert(Menu.of("밥", 111.0));
        repository.insert(Menu.of("김치", 111.0));
        repository.insert(Menu.of("라면", 111.0));

        assertThat(repository.findAll(), is(Arrays.asList(
            Menu.of("밥", 111.0), Menu.of("김치", 111.0), Menu.of("라면", 111.0)
        )));
    }

    @Test
    public void shouldInsertAll() {
        repository.insertAll(Arrays.asList(Menu.of("밥", 111.0), Menu.of("김치", 123.0), Menu.of("라면", 223.0)));
        assertThat(repository.findAll(), is(Arrays.asList(
                Menu.of("밥", 111.0), Menu.of("김치", 123.0), Menu.of("라면", 223.0)
        )));
    }
}
