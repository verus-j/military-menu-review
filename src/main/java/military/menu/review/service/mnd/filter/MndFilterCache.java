package military.menu.review.service.mnd.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MndFilterCache {
    private final Map<Class<?>, Object> map;

    public MndFilterCache() {
        map = new HashMap<>();
    }

    public <T> MndFilterCache initDtoList(Class<T> cls, List<T> list) {
        map.put(cls, list);
        return this;
    }

    public <E, K> MndFilterCache initEntityMap(Class<E> cls) {
        map.put(cls, new HashMap<K, E>());
        return this;
    }

    public <E, K> void putEntity(K key, E entity) {
        ((Map)map.get(entity.getClass())).put(key, entity);
    }

    public <D> List<D> findDtoList(Class<D> cls) {
        return (List<D>)map.get(cls);
    }

    public <E, K> E findEntity(Class<E> cls, K key) {
        return (E)((Map)map.get(cls)).get(key);
    }
}
