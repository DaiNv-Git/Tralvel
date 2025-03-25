package app.travelstride.Model.Jpa;

import app.travelstride.Model.TourActivity;
import app.travelstride.Model.TourDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDestinationRepository extends JpaRepository<TourDestination, Long> {
}
