package military.menu.review.service.mnd.filter;

import military.menu.review.service.mnd.filter.exception.DtoListNotInitInCacheException;
import military.menu.review.service.mnd.filter.exception.EntityMapNotInitInCacheException;
import military.menu.review.service.mnd.filter.exception.EntityNotInCacheException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MndFilterCacheTest {
    static class DTO {}
    static class Entity{}

    @Test
    public void shouldFindDtoLists() {
        List<DTO> list = new ArrayList<>();
        MndFilterCache cache = new MndFilterCache().initDtoList(DTO.class, list);

        assertThat(cache.findDtoList(DTO.class), is(list));
    }

    @Test
    public void shouldFindEntity() {
        Entity entity = new Entity();
        MndFilterCache cache = new MndFilterCache().initEntityMap(Entity.class);
        cache.putEntity(1, entity);

        assertThat(cache.findEntity(Entity.class, 1), is(entity));
    }

    @Test
    public void shouldThrowEntityMapNotInitInCacheException() {
        MndFilterCache cache = new MndFilterCache();
        assertThrows(EntityMapNotInitInCacheException.class, () -> cache.findEntity(Entity.class, 1));
    }

    @Test
    public void shouldThrowDtoListNotInitInCacheException() {
        MndFilterCache cache = new MndFilterCache();
        assertThrows(DtoListNotInitInCacheException.class, () -> cache.findDtoList(DTO.class));
    }

    @Test
    public void shouldThrowEntityNotInCacheException() {
        MndFilterCache cache = new MndFilterCache().initEntityMap(Entity.class);
        assertThrows(EntityNotInCacheException.class, () -> cache.findEntity(Entity.class, 1));
    }
}
