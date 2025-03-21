package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "tour_activity")
@Data

public class TourActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tourId;
    private Long activityId;

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

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public TourActivity() {
    }

    public TourActivity(Long id, Long tourId, Long activityId) {
        this.id = id;
        this.tourId = tourId;
        this.activityId = activityId;
    }
}




