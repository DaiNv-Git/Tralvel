package app.travelstride.Model.Jpa;

import app.travelstride.Model.Treding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TredingRepository extends JpaRepository<Treding, Long> {
    @Query("SELECT tr FROM Treding tr JOIN TourTrending ttr ON tr.id = ttr.trendingId WHERE ttr.tourId IN :tourIds")
    List<Treding> findByTourIds(@Param("tourIds") List<Long> tourIds);
}
