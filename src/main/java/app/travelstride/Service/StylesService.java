package app.travelstride.Service;

import app.travelstride.Model.Jpa.StylesRepository;
import app.travelstride.Model.Styles;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StylesService {
    @Autowired
    private  StylesRepository stylesRepository;

    public List<Styles> getAll() {
        return stylesRepository.findAll();
    }

    public Styles create(Styles styles) {
        return stylesRepository.save(styles);
    }

    public Styles update(Long id, Styles styles) {
        Styles old = stylesRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        old.setName(styles.getName());
        return stylesRepository.save(old);
    }

    public void delete(Long id) {
        stylesRepository.deleteById(id);
    }
}
