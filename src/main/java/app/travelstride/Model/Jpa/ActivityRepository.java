package app.travelstride.Model.Jpa;

import app.travelstride.Model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a JOIN TourActivity ta ON a.id = ta.activityId WHERE ta.tourId IN :tourIds")
    List<Activity> findByTourIds(@Param("tourIds") List<Long> tourIds);
}
