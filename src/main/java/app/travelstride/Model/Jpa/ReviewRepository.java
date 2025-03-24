package app.travelstride.Model.Jpa;
import app.travelstride.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByTourId(Long tourId, Pageable pageable);
    List<Review> findByTourId(Long tourId);
    void deleteByTourId(Long tourId);
    @Query("SELECT r.tourId AS tourId, COUNT(r.id) AS reviewCount, " +
            "AVG(r.overallRating) AS avgOverall, AVG(r.valueRating) AS avgValue, " +
            "AVG(r.guideRating) AS avgGuide, AVG(r.activitiesRating) AS avgActivities, " +
            "AVG(r.lodgingRating) AS avgLodging, AVG(r.transportationRating) AS avgTransportation, " +
            "AVG(r.mealsRating) AS avgMeals " +
            "FROM Review r WHERE r.tourId IN :tourIds GROUP BY r.tourId")
    List<Map<String, Object>> getReviewSummaryByTourIds(@Param("tourIds") List<Long> tourIds);


}
