package app.travelstride.Model.Jpa;

import app.travelstride.Model.Tour;
import app.travelstride.Model.TourActivity;
import app.travelstride.Model.TourDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourDestinationRepository extends JpaRepository<TourDestination, Long> {
    void deleteByTourId(Long id);

    @Query("SELECT td.tour FROM TourDestination td WHERE td.destination.destination = :destinationName")
    List<Tour> findToursByDestinationName(@Param("destinationName") String destinationName);

}
