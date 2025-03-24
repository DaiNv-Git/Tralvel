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
    private String imageUrl;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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
