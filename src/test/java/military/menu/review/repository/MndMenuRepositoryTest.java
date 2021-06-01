package military.menu.review.repository;

import military.menu.review.mndapi.MndApi;
import military.menu.review.mndapi.parser.MenuListParser;
import military.menu.review.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MndMenuRepositoryTest {
    @MockBean
    MndApi api;

    @Resource(name="mndMenuRepository")
    MenuRepository repository;

    @BeforeEach
    void setUp() {
        when(api.parse(any(MenuListParser.class)))
            .thenReturn(Arrays.asList(Menu.of("밥", 0.0), Menu.of("김치", 0.0)));
    }

    @Test
    public void shouldFindAll() {
        List<Menu> menuList = repository.findAll();
        assertThat(menuList, is(Arrays.asList(Menu.of("밥", 0.0), Menu.of("김치", 0.0))));
    }

    @Test
    public void shouldThrowUnsupportedException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            repository.findByName("밥");
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.insert(Menu.of("밥", 1.1));
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.insertAll(Arrays.asList(Menu.of("밥", 1.1)));
        });
    }
}
