package app.travelstride.Controller;

import app.travelstride.Model.Interests;
import app.travelstride.Model.Treding;
import app.travelstride.Service.InterestsService;
import app.travelstride.Service.TredingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/interests")
@RequiredArgsConstructor
public class InterestsController {

    @Autowired
    private  InterestsService interestsService;

    @GetMapping
    public List<Interests> getAll() {
        return interestsService.getAll();
    }

    @PostMapping
    public Interests create(@RequestBody Interests interests) {
        return interestsService.create(interests);
    }

    @PutMapping("/{id}")
    public Interests update(@PathVariable Long id, @RequestBody Interests interests) {
        return interestsService.update(id, interests);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        interestsService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
