package app.travelstride.Model.Jpa;
import app.travelstride.Model.TourReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TourReservationRepository  extends JpaRepository<TourReservation, Long> {
    @EntityGraph(attributePaths = "tour")
    @Query("SELECT r FROM TourReservation r " +
            "WHERE (:name IS NULL OR r.firstName LIKE %:name%) " +
            "AND (:mobile IS NULL OR r.mobilePhone LIKE %:mobile%) " +
            "AND (:tourName IS NULL OR r.tour.name LIKE %:tourName%)")
    Page<TourReservation> searchReservations(@Param("name") String name,
                                             @Param("mobile") String mobile,
                                             @Param("tourName") String tourName,
                                             Pageable pageable);

}
