package app.travelstride.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;

    private Long continentId;

    private String imageUrl;

    private String description;

    private  Boolean isShow;
    
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TourDestination> tourDestinations = new ArrayList<>();


    public Destination(Long did) {
    }

    public List<TourDestination> getTourDestinations() {
        return tourDestinations;
    }

    public Destination() {
    }

    public Destination(Long id, String destination, Long continentId, String imageUrl, String description, Boolean isShow, List<TourDestination> tourDestinations) {
        this.id = id;
        this.destination = destination;
        this.continentId = continentId;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isShow = isShow;
        this.tourDestinations = tourDestinations;
    }

    public void setTourDestinations(List<TourDestination> tourDestinations) {
        this.tourDestinations = tourDestinations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getContinentId() {
        return continentId;
    }

    public void setContinentId(Long continentId) {
        this.continentId = continentId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }
}
