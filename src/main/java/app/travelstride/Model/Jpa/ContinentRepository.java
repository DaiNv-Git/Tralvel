package app.travelstride.Model.Jpa;

import app.travelstride.Model.Continents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContinentRepository extends JpaRepository<Continents, Long> {
    List<Continents> findByContinentNameContainingIgnoreCase(String name);

}
