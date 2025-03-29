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
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Styles old = stylesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Not found"));

            old.setName(name);
            old.setDescription(description);
            if (image != null && !image.isEmpty()) {
                String uploadDir = "/home/user/Travel/BE/images/";;
                File dir = new File(uploadDir);
                // Xóa tất cả các tệp cũ trong thư mục
                if (dir.exists()) {
                    for (File subFile : dir.listFiles()) {
                        if (subFile.isFile()) {
                            subFile.delete();
                        }
                    }
                } else {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, image.getBytes());

                String imageUrl = "/images/" + fileName;
                old.setImageUrl(imageUrl);
             
            }

            stylesRepository.save(old);
            return ResponseEntity.ok(old);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        stylesService.delete(id);
        return ResponseEntity.ok("Deleted");
    }


    @PostMapping("/create")
    public ResponseEntity<?> createStyle(@RequestParam("name") String name,
                                         @RequestParam("description") String description,
                                         @RequestParam("image") MultipartFile image) {
        try {
        
            String uploadDir = "/home/user/Travel/BE/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, image.getBytes());


            String imageUrl = "/images/" + fileName;

            // Lưu vào DB
            Styles styles = new Styles();
            styles.setName(name);
            styles.setDescription(description);
            styles.setImageUrl(imageUrl);
            stylesRepository.save(styles);

            return ResponseEntity.ok(styles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }

}
