package military.menu.review.mnd.chain.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.MealDTO;
import military.menu.review.domain.dto.MenuDTO;
import military.menu.review.domain.entity.Menu;
import military.menu.review.mnd.chain.MndSaveChainCache;
import military.menu.review.repository.MenuRepository;
import military.menu.review.mnd.chain.MndSaveBodyChain;

@RequiredArgsConstructor
public class SaveMenus extends MndSaveBodyChain {
    private final MenuRepository menuRepository;

    @Override
    protected void process(MndSaveChainCache cache) {
        cache.findDtoList(MenuDTO.class).stream().forEach(menu -> save(cache, menu));
    }

    private void save(MndSaveChainCache cache, MenuDTO menu) {
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

    private void saveToCache(MndSaveChainCache cache, Menu menu) {
        cache.putEntity(menu.getName(), menu);
    }
}
