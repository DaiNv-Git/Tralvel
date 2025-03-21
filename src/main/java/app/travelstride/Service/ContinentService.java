package app.travelstride.Service;

import app.travelstride.Model.Continents;
import app.travelstride.Model.Jpa.ContinentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContinentService {

    @Autowired
    private ContinentRepository continentRepository;

    public List<Continents> getAllContinents() {
        return continentRepository.findAll();
    }

    public void createContinent(Continents continent) {
        continentRepository.save(continent);
    }

    public void updateContinent(Long id, Continents updatedContinent) {
        Continents continent = continentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
        continent.setContinentName(updatedContinent.getContinentName());
        continentRepository.save(continent);
    }

    public void deleteContinent(Long id) {
        continentRepository.deleteById(id);
    }

    public List<Continents> searchByName(String name) {
        return continentRepository.findByContinentNameContainingIgnoreCase(name);
    }
}
