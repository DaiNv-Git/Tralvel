package app.travelstride.Controller;

import app.travelstride.Model.Destination;
import app.travelstride.Model.dto.DestinationDTO;
import app.travelstride.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    // ✅ Get All
    @GetMapping
    public List<Map<String, Object>> getAll() {
        return destinationService.getAllDestinations();
    }

    // ✅ Add
    @PostMapping
    public ResponseEntity<String> create(@RequestBody DestinationDTO dto) {
        destinationService.createDestination(dto);
        return ResponseEntity.ok("Created successfully");
    }

    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody DestinationDTO dto) {
        destinationService.updateDestination(id, dto);
        return ResponseEntity.ok("Updated successfully");
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // ✅ Get by Continent
    @GetMapping("/continent/{continentId}")
    public List<Destination> getByContinent(@PathVariable int continentId) {
        return destinationService.getByContinent(continentId);
    }
}
