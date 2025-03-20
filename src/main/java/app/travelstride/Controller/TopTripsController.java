package app.travelstride.Controller;
import app.travelstride.Model.Activity;
import app.travelstride.Model.TopTrips;
import app.travelstride.Service.ActivityService;
import app.travelstride.Service.TopTripsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/top-trips")
@RequiredArgsConstructor
public class TopTripsController {
    @Autowired
    private  TopTripsService topTripsService;

    @GetMapping
    public List<TopTrips> getAll() {
        return topTripsService.getAll();
    }

    @PostMapping
    public TopTrips create(@RequestBody TopTrips topTrips) {
        return topTripsService.create(topTrips);
    }

    @PutMapping("/{id}")
    public TopTrips update(@PathVariable Long id, @RequestBody TopTrips topTrips) {
        return topTripsService.update(id, topTrips);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        topTripsService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
