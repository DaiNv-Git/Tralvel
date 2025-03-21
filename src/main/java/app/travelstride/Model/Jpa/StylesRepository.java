package app.travelstride.Model.Jpa;

import app.travelstride.Model.Styles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StylesRepository extends JpaRepository<Styles, Long> {
    @Query("SELECT s FROM Styles s JOIN TourStyle ts ON s.id = ts.styleId WHERE ts.tourId IN :tourIds")
    List<Styles> findByTourIds(@Param("tourIds") List<Long> tourIds);   
}
