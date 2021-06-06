package military.menu.review.mnd.chain.impl;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.entity.DailyMeal;
import military.menu.review.mnd.chain.MndSaveChainCache;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.mnd.chain.MndSaveBodyChain;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SaveDailyMeals extends MndSaveBodyChain {
    private final DailyMealRepository dailyMealRepository;

    @Override
    protected void process(MndSaveChainCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream().forEach(d -> save(cache, d));
    }

    private void save(MndSaveChainCache cache, DailyMealDTO dto) {
        DailyMeal dailyMeal = findByDate(dto.getDate());

        if(notInDB(dailyMeal)) {
            dailyMeal = saveToDB(toEntity(dto));
        }

        saveToCache(cache, dailyMeal);
    }

    private DailyMeal findByDate(LocalDate date) {
        return dailyMealRepository.findByDate(date);
    }

    private boolean notInDB(DailyMeal dailyMeal) {
        return dailyMeal == null;
    }

    private DailyMeal saveToDB(DailyMeal dailyMeal) {
        return dailyMealRepository.save(dailyMeal);
    }

    private DailyMeal toEntity(DailyMealDTO dto) {
        return DailyMeal.from(dto.getDate());
    }

    private void saveToCache(MndSaveChainCache cache, DailyMeal dailyMeal) {
        cache.putEntity(dailyMeal.getDate(), dailyMeal);
    }
}
