package app.travelstride.Model.Jpa;

import app.travelstride.Model.Interests;
import app.travelstride.Model.TourImage;
import app.travelstride.Model.TourInterests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourInterestsRepository  extends JpaRepository<TourInterests, Long> {
    void deleteByTourId(Long id);
    List<Interests> findByTourId(Long id);
}
