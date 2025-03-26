package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tour_interests")
public class TourInterests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long interestId;
    private Long tourId;

    public TourInterests() {
    }

    public TourInterests(Long id, Long interestId) {
        this.id = id;
        this.interestId = interestId;
    }

    public TourInterests(Object o, Long iid, Long id) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInterestId() {
        return interestId;
    }

    public void setInterestId(Long interestId) {
        this.interestId = interestId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }
}
