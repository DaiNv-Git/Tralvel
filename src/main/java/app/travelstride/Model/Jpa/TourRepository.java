package app.travelstride.Model.Jpa;

import app.travelstride.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository  extends JpaRepository<Tour, Long> {

}
