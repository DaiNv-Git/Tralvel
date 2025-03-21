package app.travelstride.Model.Jpa;

import app.travelstride.Model.Interests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestsRepository  extends JpaRepository<Interests, Long> {
    @Query("SELECT i FROM Interests i JOIN TourInterests ti ON i.id = ti.interestId WHERE ti.tourId IN :tourIds")
    List<Interests> findByTourIds(@Param("tourIds") List<Long> tourIds);
}
