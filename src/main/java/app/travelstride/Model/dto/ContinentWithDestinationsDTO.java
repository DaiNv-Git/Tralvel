package app.travelstride.Model.dto;

import app.travelstride.Model.Continents;
import app.travelstride.Model.Destination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ContinentWithDestinationsDTO {
    private ContinentResponseDTO continent;
    private List<DestinationResponseDTO> destinations;

    public ContinentResponseDTO getContinent() {
        return continent;
    }

    public void setContinent(ContinentResponseDTO continent) {
        this.continent = continent;
    }

    public List<DestinationResponseDTO> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationResponseDTO> destinations) {
        this.destinations = destinations;
    }
}
