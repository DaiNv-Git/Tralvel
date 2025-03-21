package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tour_treding")
@Data
public class TourTrending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tourId;
    private Long trendingId;

    public TourTrending(Long id, Long tourId, Long trendingId) {
        this.id = id;
        this.tourId = tourId;
        this.trendingId = trendingId;
    }

    public TourTrending() {
    }

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

    public Long getTrendingId() {
        return trendingId;
    }

    public void setTrendingId(Long trendingId) {
        this.trendingId = trendingId;
    }
}
