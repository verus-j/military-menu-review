package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.repository.DailyMealRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyMealService {
    private final DailyMealRepository dailyMealRepository;

    public List<DailyMealDTO> findByDateBetween(LocalDate start, LocalDate end) {
        return dailyMealRepository.findByDateBetween(start, end)
            .stream().map(DailyMealDTO::new).collect(toList());
    }
}
