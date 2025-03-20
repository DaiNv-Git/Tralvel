package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tourId;            // Có thể null nếu review chung cho công ty
    private String companyName;     // Nếu là review tổng thể công ty

    private String nickname;        // Người đánh giá nhập
    private String reviewSummary;   // Summary của review
    @Column(length = 3000)
    private String reviewContent;   // Nội dung chi tiết

    private int overallRating;      // Overall*
    private int valueRating;        // Value
    private int guideRating;        // Guide
    private int activitiesRating;   // Activities & Attractions
    private int lodgingRating;      // Lodging
    private int transportationRating; // Transportation
    private int mealsRating;        // Meals

    private LocalDate travelDate;   // 02/26/2025

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getReviewSummary() {
        return reviewSummary;
    }

    public void setReviewSummary(String reviewSummary) {
        this.reviewSummary = reviewSummary;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(int overallRating) {
        this.overallRating = overallRating;
    }

    public int getValueRating() {
        return valueRating;
    }

    public void setValueRating(int valueRating) {
        this.valueRating = valueRating;
    }

    public int getGuideRating() {
        return guideRating;
    }

    public void setGuideRating(int guideRating) {
        this.guideRating = guideRating;
    }

    public int getActivitiesRating() {
        return activitiesRating;
    }

    public void setActivitiesRating(int activitiesRating) {
        this.activitiesRating = activitiesRating;
    }

    public int getLodgingRating() {
        return lodgingRating;
    }

    public void setLodgingRating(int lodgingRating) {
        this.lodgingRating = lodgingRating;
    }

    public int getTransportationRating() {
        return transportationRating;
    }

    public void setTransportationRating(int transportationRating) {
        this.transportationRating = transportationRating;
    }

    public int getMealsRating() {
        return mealsRating;
    }

    public void setMealsRating(int mealsRating) {
        this.mealsRating = mealsRating;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
