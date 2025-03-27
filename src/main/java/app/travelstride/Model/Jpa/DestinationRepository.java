package app.travelstride.Model.Jpa;

import app.travelstride.Model.Destination;
import app.travelstride.Model.dto.DestinationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
    SELECT new app.travelstride.Model.dto.DestinationResponse(
        d.id, d.destination, d.continentId, d.imageUrl, d.description, COUNT(DISTINCT td.tour.id)
    )
    FROM Destination d
    LEFT JOIN TourDestination td ON d.id = td.destination.id
    GROUP BY d.id, d.destination, d.continentId, d.imageUrl, d.description
""")
    List<DestinationResponse> getDestinationWithTourCount();


    List<Destination> findByContinentId(Long continentId);
    @Query("""
    SELECT d FROM Destination d
    JOIN TourDestination td ON d.id = td.destination.id
    WHERE td.tour.id = :tourId
""")
    List<Destination> findByTourId(@Param("tourId") Long tourId);
    List<Destination> findByContinentIdIn(List<Long> continentIds);

    @Query("SELECT d FROM Destination d JOIN Continents c ON d.continentId = c.continentId " +
            "WHERE (:search IS NULL OR LOWER(d.destination) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Destination> searchDestinations(String search, Pageable pageable);
}
