package military.menu.review.service.mnd.filter.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.MenuDTO;
import military.menu.review.domain.Menu;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.repository.menu.MenuRepository;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;

@RequiredArgsConstructor
public class SaveMenusFilter extends MndSaveProcessFilter {
    private final MenuRepository menuRepository;

    @Override
    protected void process(MndFilterCache cache) {
        cache.findDtoList(MenuDTO.class).stream().forEach(menu -> save(cache, menu));
    }

    private void save(MndFilterCache cache, MenuDTO menu) {
        Menu m = findByName(menu.getName());

        if(notInDB(m)) {
            m = saveToDB(toEntity(menu));
        }

        saveToCache(cache, m);
    }

    private Menu findByName(String name) {
        return menuRepository.findByName(name);
    }

    private boolean notInDB(Menu menu) {
        return menu == null;
    }

    private Menu toEntity(MenuDTO dto) {
        return Menu.of(dto.getName(), dto.getKcal());
    }

    private Menu saveToDB(Menu menu) {
        return menuRepository.save(menu);
    }

    private void saveToCache(MndFilterCache cache, Menu menu) {
        cache.putEntity(menu.getName(), menu);
    }
}
