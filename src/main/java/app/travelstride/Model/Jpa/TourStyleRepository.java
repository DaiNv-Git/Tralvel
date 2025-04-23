package app.travelstride.Model.Jpa;

import app.travelstride.Model.TourStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourStyleRepository   extends JpaRepository<TourStyle, Long> {
    void deleteByTourId(Long tourId);
    List<TourStyle> findByTourId(Long id);

    List<TourStyle> findByTourIdIn(List<Long> tourIds);

}
