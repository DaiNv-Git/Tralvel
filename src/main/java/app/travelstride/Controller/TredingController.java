package app.travelstride.Controller;

import app.travelstride.Model.Treding;
import app.travelstride.Service.TredingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treding")
public class TredingController {
    @Autowired
    private TredingService tredingService;

    // ✅ GET ALL
    @GetMapping
    public List<Treding> getAll() {
        return tredingService.getAll();
    }

    // ✅ CREATE
    @PostMapping
    public Treding create(@RequestBody Treding treding) {
        return tredingService.create(treding);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Treding update(@PathVariable Long id, @RequestBody Treding treding) {
        return tredingService.update(id, treding);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        tredingService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
