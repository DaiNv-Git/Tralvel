package app.travelstride.Model.dto;

public class DestinationAll {
    private Long id;
    private String destination;
    private String continentName;
    private String imageUrl;
    private String description;
    private Boolean isShow;

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

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
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

    public DestinationAll(Long id, String destination, String continentName, String imageUrl, String description, Boolean isShow) {
        this.id = id;
        this.destination = destination;
        this.continentName = continentName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isShow = isShow;
    }
}
