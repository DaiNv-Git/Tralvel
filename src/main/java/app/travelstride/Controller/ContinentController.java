package app.travelstride.Controller;

import app.travelstride.Model.Continents;
import app.travelstride.Service.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/continents")
@CrossOrigin("*")  // Cho phép gọi từ FE (React, Postman)
public class ContinentController {

    @Autowired
    private ContinentService continentService;

    // Lấy toàn bộ continents
    @GetMapping
    public List<Continents> getAllContinents() {
        return continentService.getAllContinents();
    }

    // Thêm mới continent
    @PostMapping
    public void createContinent(@RequestBody Continents continent) {
        continentService.createContinent(continent);
    }

    // Sửa continent theo ID
    @PutMapping("/{id}")
    public void updateContinent(@PathVariable Long id, @RequestBody Continents continent) {
        continentService.updateContinent(id, continent);
    }

    // Xoá continent theo ID
    @DeleteMapping("/{id}")
    public void deleteContinent(@PathVariable Long id) {
        continentService.deleteContinent(id);
    }

    // Tìm kiếm continent theo name (LIKE)
    @GetMapping("/search")
    public List<Continents> searchByName(@RequestParam String name) {
        return continentService.searchByName(name);
    }
}
