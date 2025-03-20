package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Continents")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Continents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long continentId;

    @Column(name = "ContinentName")
    private String continentName;

    public Long getContinentId() {
        return continentId;
    }

    public void setContinentId(Long continentId) {
        this.continentId = continentId;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }
}
