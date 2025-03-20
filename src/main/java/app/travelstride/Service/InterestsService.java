package app.travelstride.Service;

import app.travelstride.Model.Interests;
import app.travelstride.Model.Jpa.InterestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestsService {
    @Autowired
    private  InterestsRepository interestsRepository;

    public List<Interests> getAll() {
        return interestsRepository.findAll();
    }

    public Interests create(Interests interests) {
        return interestsRepository.save(interests);
    }

    public Interests update(Long id, Interests interests) {
        Interests old = interestsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interest not found"));
        old.setName(interests.getName());
        return interestsRepository.save(old);
    }

    public void delete(Long id) {
        interestsRepository.deleteById(id);
    }
}
