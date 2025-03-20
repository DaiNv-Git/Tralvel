package app.travelstride.Model.Jpa;

import app.travelstride.Model.TopTrips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopTripsRepository extends JpaRepository<TopTrips, Long> {
}
