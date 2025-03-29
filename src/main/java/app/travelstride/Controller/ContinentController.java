package app.travelstride.Controller;

import app.travelstride.Model.Continents;
import app.travelstride.Model.Jpa.ContinentRepository;
import app.travelstride.Service.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/continents")
@CrossOrigin("*")  // Cho phép gọi từ FE (React, Postman)
public class ContinentController {

     @Autowired
    private ContinentRepository continentRepository;

    @Autowired
    private ContinentService continentService;

    // Lấy toàn bộ continents
    @GetMapping
    public List<Continents> getAllContinents() {
        return continentService.getAllContinents();
    }

    // Thêm mới continent
    @PostMapping("/create")
    public ResponseEntity<?> createContinent(@RequestParam("continentName") String continentName,
                                             @RequestParam("description") String description,
                                             @RequestParam("file") MultipartFile file) {
        try {
            // Tạo thư mục uploads nếu chưa có
            String uploadDir = "uploads/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Lưu file
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            // Đường dẫn ảnh lưu vào DB
            String imageUrl = "/images/" + fileName;

            // Lưu continent vào DB
            Continents continent = new Continents();
            continent.setContinentName(continentName);
            continent.setImageUrl(imageUrl);
            continent.setDescription(description);
            continentRepository.save(continent);

            return ResponseEntity.ok(continent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }


    // Sửa continent theo ID
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateContinent(@PathVariable Long id,
                                             @RequestParam("continentName") String continentName,
                                             @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            Continents continent = continentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Continent not found"));

            continent.setContinentName(continentName);

            if (file != null && !file.isEmpty()) {
                // Xử lý upload ảnh mới
                String uploadDir = "uploads/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, file.getBytes());

                // Cập nhật lại imageUrl
                String imageUrl = "/images/" + fileName;
                continent.setImageUrl(imageUrl);
            }

            continentRepository.save(continent);
            return ResponseEntity.ok(continent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
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
