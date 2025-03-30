package app.travelstride.Service;


import app.travelstride.Model.Jpa.ThemeRepository;
import app.travelstride.Model.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    public Optional<Theme> getThemeById(Long id) {
        return themeRepository.findById(id);
    }

    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    public Theme updateTheme(Long id, Theme themeDetails) {
        return themeRepository.findById(id)
                .map(theme -> {
                    theme.setName(themeDetails.getName());
                    return themeRepository.save(theme);
                })
                .orElseThrow(() -> new RuntimeException("Theme not found with id " + id));
    }

    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }
}
