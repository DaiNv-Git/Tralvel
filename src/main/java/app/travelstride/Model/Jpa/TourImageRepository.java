package app.travelstride.Model.Jpa;

import app.travelstride.Model.TourImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TourImageRepository extends JpaRepository<TourImage, Long> {
    List<TourImage> findByTourId(Long tourId);

    void deleteAllByTourId(Long tourId);
}