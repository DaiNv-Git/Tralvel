package app.travelstride.Model.Jpa;

import app.travelstride.Model.TourTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TourThemeRepository extends JpaRepository<TourTheme, Long> {
    List<TourTheme> findByTourId(Long tourId);

    void deleteByTourId(Long tourId);
}
