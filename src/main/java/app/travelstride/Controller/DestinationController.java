package app.travelstride.Controller;

import app.travelstride.Model.Continents;
import app.travelstride.Model.Destination;
import app.travelstride.Model.Jpa.ContinentRepository;
import app.travelstride.Model.Jpa.DestinationRepository;
import app.travelstride.Model.dto.*;
import app.travelstride.Service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;
    @Autowired
    private ContinentRepository continentsRepository;

    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private ContinentRepository continentRepository;

    @GetMapping("/all")
    public List<ContinentWithDestinationsDTO> getContinentsWithDestinations() {
        List<Continents> continents = continentRepository.findAll();
        List<ContinentWithDestinationsDTO> result = new ArrayList<>();

        for (Continents continent : continents) {
            List<Destination> destinationList = destinationRepository.findByContinentId(continent.getContinentId());

            List<DestinationResponseDTO> destinationDTOs = destinationList.stream().map(dest -> {
                DestinationResponseDTO dto = new DestinationResponseDTO();
                dto.setId(dest.getId());
                dto.setDestination(dest.getDestination());
                dto.setContinentId(dest.getContinentId());
                dto.setImageUrl(dest.getImageUrl());
                dto.setDescription(dest.getDescription());
                dto.setTourNumber((long) dest.getTourDestinations().size()); // đếm số tour
                return dto;
            }).toList();

            ContinentResponseDTO continentDTO = new ContinentResponseDTO();
            continentDTO.setContinentId(continent.getContinentId());
            continentDTO.setContinentName(continent.getContinentName());
            continentDTO.setImageUrl(continent.getImageUrl());
            continentDTO.setDescription(continent.getDescription());

            ContinentWithDestinationsDTO dto = new ContinentWithDestinationsDTO();
            dto.setContinent(continentDTO);
            dto.setDestinations(destinationDTOs);
            result.add(dto);
        }

        return result;
    }


  
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
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> update(
            @PathVariable Long id,
            @RequestParam("destination") String destination,
            @RequestParam("description") String description,
            @RequestParam("continentId") Long continentId,
            @RequestParam("isShow") boolean isShow,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        DestinationDTO dto = new DestinationDTO();
        dto.setDestination(destination);
        dto.setDescription(description);
        dto.setShow(isShow);
        dto.setContinentId(continentId);
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

    @GetMapping("/searchAdmin")
    public Page<DestinationAll> searchDestinations(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        return destinationService.searchDestinations(search, pageable);
    }
}
