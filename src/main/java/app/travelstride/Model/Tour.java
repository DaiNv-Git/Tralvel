package app.travelstride.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tour")
@Getter
@Setter
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tripId;            // Mã chuyến đi

    private String name;

    private String lodgingLevel;

    private String tripType;

    private String physicalLevel;

    private String tripPace;

    @Lob
    private String highlights;

    @Lob
    private String tripAbout;

    @Lob
    private String itineraryFocus;

    private String groupSize;

    private String ageRange;

    private Integer minGroupSize;

    private Integer maxGroupSize;

    private String attractions;

    private String destinations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLodgingLevel() {
        return lodgingLevel;
    }

    public void setLodgingLevel(String lodgingLevel) {
        this.lodgingLevel = lodgingLevel;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getPhysicalLevel() {
        return physicalLevel;
    }

    public void setPhysicalLevel(String physicalLevel) {
        this.physicalLevel = physicalLevel;
    }

    public String getTripPace() {
        return tripPace;
    }

    public void setTripPace(String tripPace) {
        this.tripPace = tripPace;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getTripAbout() {
        return tripAbout;
    }

    public void setTripAbout(String tripAbout) {
        this.tripAbout = tripAbout;
    }

    public String getItineraryFocus() {
        return itineraryFocus;
    }

    public void setItineraryFocus(String itineraryFocus) {
        this.itineraryFocus = itineraryFocus;
    }

    public String getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(String groupSize) {
        this.groupSize = groupSize;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public Integer getMinGroupSize() {
        return minGroupSize;
    }

    public void setMinGroupSize(Integer minGroupSize) {
        this.minGroupSize = minGroupSize;
    }

    public Integer getMaxGroupSize() {
        return maxGroupSize;
    }

    public void setMaxGroupSize(Integer maxGroupSize) {
        this.maxGroupSize = maxGroupSize;
    }

    public String getAttractions() {
        return attractions;
    }

    public void setAttractions(String attractions) {
        this.attractions = attractions;
    }

    public String getDestinations() {
        return destinations;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }
}
