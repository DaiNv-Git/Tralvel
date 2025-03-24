package app.travelstride.Model.dto;

import app.travelstride.Model.Continents;
import app.travelstride.Model.Destination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ContinentWithDestinationsDTO {
    private Continents continent;
    private List<Destination> destinations;

    public ContinentWithDestinationsDTO() {
    }

    public ContinentWithDestinationsDTO(Continents continent, List<Destination> destinations) {
        this.continent = continent;
        this.destinations = destinations;
    }

    public Continents getContinent() {
        return continent;
    }

    public void setContinent(Continents continent) {
        this.continent = continent;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }
}
