package military.menu.review.repository;

import military.menu.review.domain.Member;
import military.menu.review.domain.Menu;
import military.menu.review.service.dto.MenuDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByName(String name);

    @Query("select distinct mn from Like l join l.menu mn " +
            "join l.member m join mn.mealMenus mm " +
            "join mm.meal ma join ma.dailyMeal d " +
            "where m.username=:username and d.date between :start and :end")
    List<Menu> findByMemberLikedAndDateBetween(@Param("username") String username, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select distinct mn from Menu mn " +
            "join mn.mealMenus mm join mm.meal ma join ma.dailyMeal d " +
            "where d.date between :start and :end")
    List<Menu> findByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);


    @Query("select distinct mn from Menu mn " +
            "join mn.mealMenus mm join mm.meal ma join ma.dailyMeal d " +
            "where d.date=:date")
    List<Menu> findByDate(@Param("date") LocalDate date);

    @Query("select distinct mn from Like l join l.menu mn " +
            "join l.member m join mn.mealMenus mm " +
            "join mm.meal ma join ma.dailyMeal d " +
            "where m.username=:username and d.date=:date")
    List<Menu> findByMemberLikedAndDate(@Param("username") String username, @Param("date") LocalDate date);
}
