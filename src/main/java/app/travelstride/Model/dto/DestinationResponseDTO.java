package app.travelstride.Model.dto;

public class DestinationResponseDTO {
    private Long id;
    private String destination;
    private Long continentId;
    private String imageUrl;
    private String description;
    private Long tourNumber;  // nếu muốn đếm số tour

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

    public Long getTourNumber() {
        return tourNumber;
    }

    public void setTourNumber(Long tourNumber) {
        this.tourNumber = tourNumber;
    }
}