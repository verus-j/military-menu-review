package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.dto.DailyMealDTO;
import military.menu.review.domain.entity.DailyMeal;
import military.menu.review.repository.DailyMealRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyMealService {
    private final DailyMealRepository dailyMealRepository;

    public DailyMealDTO findByDate(LocalDate date) {
        return new DailyMealDTO(dailyMealRepository.findByDate(adjustDate(date)));
    }

    public List<DailyMealDTO> findByDateBetween(LocalDate start, LocalDate end) {
        return dailyMealRepository.findByDateBetween(adjustDate(start), adjustDate(end))
            .stream().map(DailyMealDTO::new).collect(toList());
    }

    private LocalDate adjustDate(LocalDate date) {
        return date.plusDays(1);
    }
}
