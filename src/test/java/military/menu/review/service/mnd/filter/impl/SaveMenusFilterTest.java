package military.menu.review.service.mnd.filter.impl;

import military.menu.review.repository.MenuRepository;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.service.mnd.filter.MndFilterCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static military.menu.review.service.mnd.filter.impl.DTOUtils.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class SaveMenusFilterTest {
    private SaveMenusFilter filter;
    private MndFilterCache cache;
    @Autowired
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        filter = new SaveMenusFilter(menuRepository);
        when(cache.findDtoList(MenuDTO.class)).thenReturn(menuDtos());
    }

    @Test
    public void shouldSaveMenusToDB() {
        filter.process(ca)
    }
}
