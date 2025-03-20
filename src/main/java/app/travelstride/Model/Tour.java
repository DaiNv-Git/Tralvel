package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String focus;
    private String groupSize;
    private String ageRange;
    private String flightTransport;
    private String startCity;
    private String endCity;
    private BigDecimal priceFrom;
    private BigDecimal pricePerDay;
    private LocalDate lastUpdated;

    @Column(columnDefinition = "TEXT")
    private String description;
}
