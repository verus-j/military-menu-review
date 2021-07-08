package military.menu.review.service;

import lombok.RequiredArgsConstructor;
import military.menu.review.domain.MealMenu;
import military.menu.review.domain.MealType;
import military.menu.review.repository.mealmenu.MealMenuRepository;
import military.menu.review.service.dto.DailyMealDTO;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MealMenuService {
    private final MealMenuRepository mealMenuRepository;

    public List<DailyMealDTO> findByDateBetween(LocalDate start, LocalDate end) {
        List<MealMenu> mealMenus = mealMenuRepository.findByDateBetween(start, end);

        return convertToDTO(mealMenus);
    }

    private List<DailyMealDTO> convertToDTO(List<MealMenu> mealMenus) {
        Map<LocalDate, DailyMealDTO> map = new HashMap<>();

        for(MealMenu mealMenu : mealMenus) {
            DailyMealDTO dailyMealDTO = findDailyMealDTO(map, mealMenu);
            addMenu(dailyMealDTO, mealMenu);
        }

        return map.keySet().stream().map(k -> map.get(k)).sorted((a, b) -> a.getDate().compareTo(b.getDate())).collect(Collectors.toList());
    }

    private DailyMealDTO findDailyMealDTO(Map<LocalDate, DailyMealDTO> map, MealMenu mealMenu) {
        if(map.get(mealMenu.getMeal().getDate()) == null) {
            map.put(mealMenu.getMeal().getDate(), DailyMealDTO.of(mealMenu.getMeal().getDate()));
        }

        DailyMealDTO dto = map.get(mealMenu.getMeal().getDate());

        if(mealMenu.getMeal().getType().equals(MealType.BREAKFAST)) {
            if(dto.getBreakfast().getId() == null) {
                dto.getBreakfast().setId(mealMenu.getMeal().getId());
            }
        } else if(mealMenu.getMeal().getType().equals(MealType.LUNCH)) {
            if(dto.getLunch().getId() == null) {
                dto.getLunch().setId(mealMenu.getMeal().getId());
            }
        } else {
            if(dto.getDinner().getId() == null) {
                dto.getDinner().setId(mealMenu.getMeal().getId());
            }
        }

        return dto;
    }

    private void addMenu(DailyMealDTO dto, MealMenu mealMenu) {
        switch (mealMenu.getMeal().getType()) {
            case BREAKFAST:
                dto.addBreakfastMenu(new MenuDTO(mealMenu.getMenu()));
                break;
            case LUNCH:
                dto.addLunchMenu(new MenuDTO(mealMenu.getMenu()));
                break;
            case DINNER:
                dto.addDinnerMenu(new MenuDTO(mealMenu.getMenu()));
                break;
        }
    }
}
