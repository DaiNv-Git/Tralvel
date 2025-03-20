package app.travelstride.Model.Jpa;

import app.travelstride.Model.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRequestRepository extends JpaRepository<TripRequest, Long>, JpaSpecificationExecutor<TripRequest> {
}
