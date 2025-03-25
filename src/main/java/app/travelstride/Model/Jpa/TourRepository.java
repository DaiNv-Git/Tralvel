package app.travelstride.Model.Jpa;

import app.travelstride.Model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TourRepository  extends JpaRepository<Tour, Long> {
    @Query(value = """
        SELECT DISTINCT t.*
        FROM tour t
        LEFT JOIN tour_theme tt ON t.id = tt.tour_id
        LEFT JOIN theme th ON tt.theme_id = th.id
        LEFT JOIN tour_treding tr ON t.id = tr.tour_id
        LEFT JOIN trending trg ON tr.trending_id = trg.id
        LEFT JOIN tour_activity ta ON t.id = ta.tour_id
        LEFT JOIN activity act ON ta.activity_id = act.id
        LEFT JOIN tour_interests ti ON t.id = ti.tour_id
        LEFT JOIN interests i ON ti.interest_id = i.id
        WHERE (:tourId IS NULL OR t.id = :tourId)
          AND (:tourName IS NULL OR t.name LIKE %:tourName%)
          AND (:themeName IS NULL OR th.name LIKE %:themeName%)
          AND (:trendingName IS NULL OR trg.name LIKE %:trendingName%)
          AND (:activityName IS NULL OR act.name LIKE %:activityName%)
          AND (:interestName IS NULL OR i.name LIKE %:interestName%)
          AND (:attractions IS NULL OR t.attractions LIKE %:attractions%)
          AND (:destinations IS NULL OR t.destinations LIKE %:destinations%)
        """,
            countQuery = "SELECT count(*) FROM tour t " +
                    "LEFT JOIN tour_theme tt ON t.id = tt.tour_id " +
                    "LEFT JOIN theme th ON tt.theme_id = th.id " +
                    "LEFT JOIN tour_trending tr ON t.id = tr.tour_id " +
                    "LEFT JOIN trending trg ON tr.trending_id = trg.id " +
                    "LEFT JOIN tour_activity ta ON t.id = ta.tour_id " +
                    "LEFT JOIN activity act ON ta.activity_id = act.id " +
                    "LEFT JOIN tour_interests ti ON t.id = ti.tour_id " +
                    "LEFT JOIN interests i ON ti.interest_id = i.id " +
                    "WHERE (:tourId IS NULL OR t.id = :tourId) " +
                    "AND (:tourName IS NULL OR t.name LIKE %:tourName%) " +
                    "AND (:themeName IS NULL OR th.name LIKE %:themeName%) " +
                    "AND (:trendingName IS NULL OR trg.name LIKE %:trendingName%) " +
                    "AND (:activityName IS NULL OR act.name LIKE %:activityName%) " +
                    "AND (:interestName IS NULL OR i.name LIKE %:interestName%) " +
                    "AND (:attractions IS NULL OR t.attractions LIKE %:attractions%) " +
                    "AND (:destinations IS NULL OR t.destinations LIKE %:destinations%)"
            , nativeQuery = true)
    Page<Tour> searchAll(@Param("tourId") Long tourId,
                         @Param("tourName") String tourName,
                         @Param("themeName") String themeName,
                         @Param("trendingName") String trendingName,
                         @Param("activityName") String activityName,
                         @Param("interestName") String interestName,
                         @Param("attractions") String attractions,
                         @Param("destinations") String destinations,
                         Pageable pageable);


    @Query("""
    SELECT t FROM Tour t
    LEFT JOIN TourTheme tt ON t.id = tt.tourId
    LEFT JOIN Theme th ON tt.themeId = th.themeId
    LEFT JOIN TourTrending ttr ON t.id = ttr.tourId
    LEFT JOIN Treding tr ON ttr.trendingId = tr.id
    LEFT JOIN TourActivity ta ON t.id = ta.tourId
    LEFT JOIN Activity act ON ta.activityId = act.id
    LEFT JOIN TourInterests ti ON t.id = ti.tourId
    LEFT JOIN Interests i ON ti.interestId = i.id
    LEFT JOIN TourStyle ts ON t.id = ts.tourId
    LEFT JOIN Styles s ON ts.styleId = s.id
    WHERE (:keyword IS NULL 
        OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(t.attractions) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(t.destinations) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR STR(t.id) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(th.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(tr.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(act.activity) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
""")
    Page<Tour> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);






    List<Tour> findByIsTrending(Integer isTrending);

}
