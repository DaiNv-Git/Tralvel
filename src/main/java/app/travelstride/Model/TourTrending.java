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
}
