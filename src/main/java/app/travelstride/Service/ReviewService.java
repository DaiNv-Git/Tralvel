package app.travelstride.Service;

import app.travelstride.Model.Jpa.ReviewRepository;
import app.travelstride.Model.Review;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    // Create
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    // Read
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }

    // Update
    public Review updateReview(Long id, Review updatedReview) {
        Review review = getReviewById(id); // load để tránh StaleObjectStateException
        review.setTourId(updatedReview.getTourId());
        review.setCompanyName(updatedReview.getCompanyName());
        review.setNickname(updatedReview.getNickname());
        review.setReviewSummary(updatedReview.getReviewSummary());
        review.setReviewContent(updatedReview.getReviewContent());
        review.setOverallRating(updatedReview.getOverallRating());
        review.setValueRating(updatedReview.getValueRating());
        review.setGuideRating(updatedReview.getGuideRating());
        review.setActivitiesRating(updatedReview.getActivitiesRating());
        review.setLodgingRating(updatedReview.getLodgingRating());
        review.setTransportationRating(updatedReview.getTransportationRating());
        review.setMealsRating(updatedReview.getMealsRating());
        review.setTravelDate(updatedReview.getTravelDate());
        return reviewRepository.save(review);
    }

    // Delete
    public void deleteReview(Long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }

    public Page<Review> getReviews(Long tourId, Pageable pageable) {
        if (tourId != null) {
            return reviewRepository.findByTourId(tourId, pageable);
        } else {
            return reviewRepository.findAll(pageable);
        }
    }
}
