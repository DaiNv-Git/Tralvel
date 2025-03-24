package app.travelstride.Controller;

import app.travelstride.Model.Destination;
import app.travelstride.Model.dto.DestinationDTO;
import app.travelstride.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    @PostMapping("/create")
    public ResponseEntity<String> createDestination(@RequestParam("destination") String destination,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("continentId") Long continentId,
                                                    @RequestParam("isShow") boolean isShow,
                                                    @RequestParam("image") MultipartFile image) {
        try {
            // ✅ Xử lý upload ảnh
            String uploadDir = "uploads/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, image.getBytes());

            // ✅ Tạo DTO
            DestinationDTO dto = new DestinationDTO();
            dto.setDestination(destination);
            dto.setContinentId(continentId);
            dto.setImageUrl("/images/" + fileName); // ✅ Lưu đường dẫn ảnh vào DTO
            dto.setDescription(description);
            dto.setShow(isShow);
            // ✅ Gọi service lưu DB
            destinationService.createDestination(dto);
            return ResponseEntity.ok("Created successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }


    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestPart("dto") DestinationDTO dto,
                                         @RequestPart(value = "image", required = false) MultipartFile image) {
        destinationService.updateDestination(id, dto, image);
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
