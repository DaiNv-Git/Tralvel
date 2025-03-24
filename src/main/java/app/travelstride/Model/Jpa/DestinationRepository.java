package app.travelstride.Model.Jpa;

import app.travelstride.Model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByContinentId(int continentId);

    @Query("SELECT d FROM Destination d WHERE d.isShow = :isShow")
    List<Destination> findVisibleDestinations(@Param("isShow") boolean isShow);



}
