package app.travelstride.Controller;
import app.travelstride.Model.Jpa.TypeRepository;
import app.travelstride.Model.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class TypeController {
    @Autowired
    private TypeRepository typeRepository;
    // ✅ CRUD type
    @PostMapping("/types")
    public Type createType(@RequestBody Type type) {
        return typeRepository.save(type);
    }

    @PutMapping("/types/{id}")
    public Type updateType(@PathVariable Long id, @RequestBody Type type) {
        type.setId(id);
        return typeRepository.save(type);
    }

    @DeleteMapping("/types/{id}")
    public void deleteType(@PathVariable Long id) {
        typeRepository.deleteById(id);
    }

    @GetMapping("/types")
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

}
