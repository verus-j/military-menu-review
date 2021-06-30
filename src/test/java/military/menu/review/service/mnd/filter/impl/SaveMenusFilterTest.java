package military.menu.review.service.mnd.filter.impl;

import military.menu.review.domain.Menu;
import military.menu.review.repository.menu.MenuRepository;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.service.mnd.filter.MndFilterCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;

import static military.menu.review.service.mnd.filter.impl.DTOUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
public class SaveMenusFilterTest {
    private SaveMenusFilter filter;
    private MndFilterCache cache;
    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        cache = mock(MndFilterCache.class);
        when(cache.findDtoList(MenuDTO.class)).thenReturn(menuDtos());
        filter = new SaveMenusFilter(menuRepository);
    }

    @Test
    public void shouldSaveMenusToDB() {
        filter.process(cache);
        List<MenuDTO> menus = menuRepository.findAll().stream().map(MenuDTO::new).collect(Collectors.toList());
        assertThat(menus, is(menuDtos()));
    }

    @Test
    public void shouldSaveMenusToCache() {
        filter.process(cache);

        for(MenuDTO dto : menuDtos()) {
            verify(cache).putEntity(eq(dto.getName()), any(Menu.class));
        }
    }

    @Test
    public void shouldNotSaveExistMenuToDB() {
        MenuRepository menuRepository = mock(MenuRepository.class);
        when(menuRepository.findByName("밥")).thenReturn(Menu.of("밥", 111.1));
        when(menuRepository.save(Menu.of("김치", 123.2))).thenReturn(Menu.of("김치", 123.2));
        when(menuRepository.save(Menu.of("라면", 333.3))).thenReturn(Menu.of("라면", 333.3));

        SaveMenusFilter filter = new SaveMenusFilter(menuRepository);
        filter.process(cache);

        verify(menuRepository, times(0)).save(Menu.of("밥", 111.1));
        verify(menuRepository).save(Menu.of("김치", 123.2));
        verify(menuRepository).save(Menu.of("라면", 333.3));
    }

    @Test
    public void shouldSaveAllMenuToCache() {
        menuRepository.save(Menu.of("밥", 111.1));

        filter.process(cache);

        for(MenuDTO dto : menuDtos()) {
            verify(cache).putEntity(eq(dto.getName()), any(Menu.class));
        }
    }
}
