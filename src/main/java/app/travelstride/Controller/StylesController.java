package app.travelstride.Controller;

import app.travelstride.Model.Styles;
import app.travelstride.Service.StylesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/styles")
@RequiredArgsConstructor
public class StylesController {
    @Autowired
    private  StylesService stylesService;

    @GetMapping
    public List<Styles> getAll() {
        return stylesService.getAll();
    }

    @PostMapping
    public Styles create(@RequestBody Styles styles) {
        return stylesService.create(styles);
    }

    @PutMapping("/{id}")
    public Styles update(@PathVariable Long id, @RequestBody Styles styles) {
        return stylesService.update(id, styles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        stylesService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
