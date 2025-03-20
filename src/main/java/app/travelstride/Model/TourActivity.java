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
}




