package app.travelstride.Model.Jpa;

import app.travelstride.Model.TopTrips;
import app.travelstride.Model.TourActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourActivityRepository extends JpaRepository<TourActivity, Long> {
    void deleteByTourId(Long tourId);
    List<TourActivity> findAllByTourId(Long id);
}
