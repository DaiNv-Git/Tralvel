package app.travelstride.Controller;

import app.travelstride.Model.Jpa.StylesRepository;
import app.travelstride.Model.Styles;
import app.travelstride.Service.StylesService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
@RequestMapping("/api/styles")
@RequiredArgsConstructor
public class StylesController {
    @Autowired
    private  StylesService stylesService;
    @Autowired
    private StylesRepository stylesRepository;

    @GetMapping
    public List<Styles> getAll() {
        return stylesService.getAll();
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


    @PostMapping("/create")
    public ResponseEntity<?> createStyle(@RequestParam("name") String name,
                                         @RequestParam("file") MultipartFile file) {
        try {
            // Nên upload vào folder ngoài target/resources
            String uploadDir = "uploads/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());


            String imageUrl = "/images/" + fileName;

            // Lưu vào DB
            Styles styles = new Styles();
            styles.setName(name);
            styles.setImageUrl(imageUrl);
            stylesRepository.save(styles);

            return ResponseEntity.ok(styles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

}
