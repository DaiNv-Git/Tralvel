package app.travelstride.Model.dto;

public class DestinationDTO1 {
    private Long id;
    private String destination;
    private Long continentId;
    private String imageUrl;
    private String description;
    

    public DestinationDTO1() {
    }

    public DestinationDTO1(Long id, String destination, Long continentId, String imageUrl, String description) {
        this.id = id;
        this.destination = destination;
        this.continentId = continentId;
        this.imageUrl = imageUrl;
        this.description = description;
       
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

   
}
