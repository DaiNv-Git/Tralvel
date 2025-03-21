package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "itinerary_tour")
public class ItineraryTour { @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tourId;
    private String day;
    @Lob
    private String content;
}
