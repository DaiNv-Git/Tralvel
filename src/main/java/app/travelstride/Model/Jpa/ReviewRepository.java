package app.travelstride.Model.Jpa;
import app.travelstride.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByTourId(Long tourId, Pageable pageable);
    List<Review> findByTourId(Long tourId);
    void deleteByTourId(Long tourId);

}
