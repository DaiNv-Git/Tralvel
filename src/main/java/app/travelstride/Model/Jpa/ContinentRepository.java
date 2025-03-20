package app.travelstride.Model.Jpa;

import app.travelstride.Model.Continents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepository extends JpaRepository<Continents, Long> {
}
