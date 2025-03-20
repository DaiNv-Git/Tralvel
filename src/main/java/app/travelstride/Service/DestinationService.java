package app.travelstride.Service;

import app.travelstride.Model.Continents;
import app.travelstride.Model.Destination;
import app.travelstride.Model.Jpa.ContinentRepository;
import app.travelstride.Model.Jpa.DestinationRepository;
import app.travelstride.Model.dto.DestinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ContinentRepository continentRepository;

    public List<Map<String, Object>> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Destination dest : destinations) {
            Optional<Continents> continent = continentRepository.findById(dest.getContinentId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", dest.getId());
            map.put("destination", dest.getDestination());
            map.put("continentId", dest.getContinentId());
            map.put("continentName", continent.map(Continents::getContinentName).orElse("Unknown"));
            result.add(map);
        }
        return result;
    }

    // Add Destination
    public void createDestination(DestinationDTO dto) {
        Destination dest = new Destination();
        dest.setDestination(dto.getDestination());
        dest.setContinentId(dto.getContinentId());
        destinationRepository.save(dest);
    }

    // Update Destination
    public void updateDestination(Long id, DestinationDTO dto) {
        Destination dest = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        dest.setDestination(dto.getDestination());
        dest.setContinentId(dto.getContinentId());
        destinationRepository.save(dest);
    }

    // Delete Destination
    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    // Get Destination by Continent
    public List<Destination> getByContinent(int continentId) {
        return destinationRepository.findByContinentId(continentId);
    }
}
