package military.menu.review.service.mnd.filter.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.domain.DailyMeal;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;
import military.menu.review.service.mnd.filter.impl.exception.DailyMealNotFoundException;

import java.time.LocalDate;

@RequiredArgsConstructor
public class SaveDailyMealsToCache extends MndSaveProcessFilter {
    private final DailyMealRepository dailyMealRepository;

    @Override
    protected void process(MndFilterCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream().forEach(d -> save(cache, d));
    }

    private void save(MndFilterCache cache, DailyMealDTO dto) {
        DailyMeal dailyMeal = findByDate(dto.getDate());

        if(dailyMeal == null) {
            throw new DailyMealNotFoundException();
        }

        saveToCache(cache, dailyMeal);
    }

    private DailyMeal findByDate(LocalDate date) {
        return dailyMealRepository.findByDate(date);
    }

    private void saveToCache(MndFilterCache cache, DailyMeal dailyMeal) {
        cache.putEntity(dailyMeal.getDate(), dailyMeal);
    }
}
