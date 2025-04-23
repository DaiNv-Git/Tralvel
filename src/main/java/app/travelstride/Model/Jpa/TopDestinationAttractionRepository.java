package app.travelstride.Model.Jpa;

import app.travelstride.Model.TopDestinationAttraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopDestinationAttractionRepository extends JpaRepository<TopDestinationAttraction, Long> {

    @Query("SELECT t FROM TopDestinationAttraction t JOIN t.destination d WHERE d.destination LIKE %:name%")
    Page<TopDestinationAttraction> findByDestinationNameContaining(@Param("name") String name, Pageable pageable);

    List<TopDestinationAttraction> findByDestination_Destination(String destinationName);


}

