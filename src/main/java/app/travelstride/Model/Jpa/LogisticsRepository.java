package app.travelstride.Model.Jpa;

import app.travelstride.Model.Logistics;
import app.travelstride.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogisticsRepository extends JpaRepository<Logistics, Long> {
    void deleteByTourId(long tourId);

    Logistics findByTourId(long tourId);

    Optional<Logistics> findFirstByTourId(long tourId);
}
