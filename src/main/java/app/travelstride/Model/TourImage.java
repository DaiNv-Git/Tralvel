package app.travelstride.Model;


import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "tour_images")
public class TourImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tourId;  // khóa ngoại, mapping tay

    private String imageUrl;
    private Boolean isCover = false;
}
