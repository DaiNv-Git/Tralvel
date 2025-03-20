package app.travelstride.Model.dto;

import lombok.Data;

@Data
public class DestinationDTO {
    private String destination;
    private Long continentId;

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
}
