package military.menu.review.service.mnd.filter.impl;

import military.menu.review.domain.DailyMeal;
import military.menu.review.repository.DailyMealRepository;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.mnd.filter.MndFilterCache;
import military.menu.review.service.mnd.filter.MndSaveProcessFilter;

import java.time.LocalDate;

public class SaveDailyMealsToDB extends MndSaveProcessFilter {
    private DailyMealRepository dailyMealRepository;

    public SaveDailyMealsToDB(DailyMealRepository repository) {
        dailyMealRepository = repository;
    }

    @Override
    protected void process(MndFilterCache cache) {
        cache.findDtoList(DailyMealDTO.class).stream().forEach(this::save);
    }

    private void save(DailyMealDTO dto) {
        if(notInDB(findByDate(dto.getDate()))) {
            saveToDB(toEntity(dto));
        }
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
}
