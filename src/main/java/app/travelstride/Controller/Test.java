package app.travelstride.Controller;
import app.travelstride.Model.Activity;
import app.travelstride.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class Test {
    @Autowired
    private  ActivityService activityService;

    @GetMapping
    public List<Activity> getAll() {
        return activityService.getAll();
    }

    @PostMapping
    public Activity create(@RequestBody Activity activity) {
        return activityService.create(activity);
    }

    @PutMapping("/{id}")
    public Activity update(@PathVariable Long id, @RequestBody Activity activity) {
        return activityService.update(id, activity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
