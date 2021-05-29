package military.menu.review.repository;

import military.menu.review.model.menu.Menu;
import military.menu.review.model.menu.MenuList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@SpringBootTest
public class MenuRepositoryTest {
    @Autowired
    MenuRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void shouldSaveMenu() {
        repository.insert(Menu.of("밥", 100.0, 1000));
        Optional<Menu> menuOptional = repository.find("밥");
        menuOptional.ifPresent(this::assertMenu);
    }

    @Test
    void shouldReturnOptionalEmptyWhenNotFoundMenu() {
        Optional<Menu> menuOptional = repository.find("라면");
        assertThat(menuOptional, is(Optional.empty()));
    }

    public void assertMenu(Menu m) {
        assertThat(m.getName(), is("밥"));
        assertThat(m.getCalorie(), is(100.0));
        assertThat(m.getTotalLike(), is(1000));
    }
}
