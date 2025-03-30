package app.travelstride.Model.Jpa;

import app.travelstride.Model.Styles;
import app.travelstride.Model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeRepository  extends JpaRepository<Theme, Long> {
    @Query("SELECT t FROM Theme t JOIN TourTheme tt ON t.themeId = tt.themeId WHERE tt.tourId IN :tourIds")
    List<Theme> findByTourIds(@Param("tourIds") List<Long> tourIds);
}
