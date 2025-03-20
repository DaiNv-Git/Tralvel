package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tour_includes")
@Data
public class TourInclude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tourId;

    private String includeItem;
}
