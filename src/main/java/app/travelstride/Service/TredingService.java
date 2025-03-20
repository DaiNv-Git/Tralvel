package app.travelstride.Service;

import app.travelstride.Model.Jpa.TredingRepository;
import app.travelstride.Model.Treding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TredingService {
    @Autowired
    private TredingRepository tredingRepository;

    // Get All
    public List<Treding> getAll() {
        return tredingRepository.findAll();
    }

    // Create
    public Treding create(Treding treding) {
        return tredingRepository.save(treding);
    }

    // Update
    public Treding update(Long id, Treding treding) {
        Treding existing = tredingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treding not found"));
        existing.setName(treding.getName());
        return tredingRepository.save(existing);
    }

    // Delete
    public void delete(Long id) {
        tredingRepository.deleteById(id);
    }
}
