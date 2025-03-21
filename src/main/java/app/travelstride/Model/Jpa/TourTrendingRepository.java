package app.travelstride.Model.Jpa;

import app.travelstride.Model.TourTrending;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourTrendingRepository extends JpaRepository<TourTrending, Long> {
    List<TourTrending> findByTourId(Long tourId);

    void deleteByTourId(Long tourId);
}
