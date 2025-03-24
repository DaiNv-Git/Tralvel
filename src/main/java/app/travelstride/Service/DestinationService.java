package app.travelstride.Service;

import app.travelstride.Model.Continents;
import app.travelstride.Model.Destination;
import app.travelstride.Model.Jpa.ContinentRepository;
import app.travelstride.Model.Jpa.DestinationRepository;
import app.travelstride.Model.dto.DestinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private ContinentRepository continentRepository;

    public List<Map<String, Object>> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Destination dest : destinations) {
            Optional<Continents> continent = continentRepository.findById(dest.getContinentId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", dest.getId());
            map.put("destination", dest.getDestination());
            map.put("continentId", dest.getContinentId());
            map.put("description", dest.getDescription());
            map.put("iamge", dest.getImageUrl());
            map.put("continentName", continent.map(Continents::getContinentName).orElse("Unknown"));
            result.add(map);
        }
        return result;
    }

    // Add Destination
    public void createDestination(DestinationDTO dto) {
        Destination dest = new Destination();
        dest.setDestination(dto.getDestination());
        dest.setContinentId(dto.getContinentId());
        dest.setImageUrl(dto.getImageUrl());  // ✅ Lưu đường dẫn ảnh vào DB
        dest.setIsShow(dto.getIsShow());  // ✅ Lưu đường dẫn ảnh vào DB
        destinationRepository.save(dest);
    }


    // Update Destination
    public void updateDestination(Long id, DestinationDTO dto, MultipartFile image) {
        Destination dest = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        dest.setDestination(dto.getDestination());
        dest.setContinentId(dto.getContinentId());
        dest.setDescription(dto.getDescription());

        if (image != null && !image.isEmpty()) {
            try {
                String uploadDir = "uploads/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, image.getBytes());
                dest.setImageUrl("/images/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image");
            }
        }

        destinationRepository.save(dest);
    }


    // Delete Destination
    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    // Get Destination by Continent
    public List<Destination> getByContinent(int continentId) {
        return destinationRepository.findByContinentId(continentId);
    }
    public List<Destination> getAllDestination() {
        return destinationRepository.findAll();
    }

}
