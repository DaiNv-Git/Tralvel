package app.travelstride.Service;

import app.travelstride.Config.CommonUpload;
import app.travelstride.Model.Continents;
import app.travelstride.Model.Destination;
import app.travelstride.Model.Jpa.ContinentRepository;
import app.travelstride.Model.Jpa.DestinationRepository;
import app.travelstride.Model.dto.DestinationAll;
import app.travelstride.Model.dto.DestinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private CommonUpload commonUpload;

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
        dest.setDescription(dto.getDescription());
        dest.setImageUrl(dto.getImageUrl());  // ✅ Lưu đường dẫn ảnh vào DB
        dest.setShow(dto.getShow());  // ✅ Lưu đường dẫn ảnh vào DB
        destinationRepository.save(dest);
    }


    // Update Destination
    public void updateDestination(Long id, DestinationDTO dto, MultipartFile image) {
        Destination dest = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        dest.setDestination(dto.getDestination());
        dest.setContinentId(dto.getContinentId());
        dest.setDescription(dto.getDescription());
        dest.setShow(dto.getShow());
        CommonUpload.deleteOldImage(dest.getImageUrl());
        if (image != null && !image.isEmpty()) {
            try {
                String uploadDir = "/home/user/Travel/BE/images/";
                File dir = new File(uploadDir);
                // Xóa tất cả các tệp cũ trong thư mục
                if (dir.exists()) {
                    String oldImageName = dest.getImageUrl(); // Giả sử bạn lưu tên file trong trường `imageName` của `old`

                    // Kiểm tra tên ảnh cũ có tồn tại và xóa tệp cũ
                    if (oldImageName != null && !oldImageName.isEmpty()) {
                        // Loại bỏ "/images/" để lấy đúng tên tệp từ tên ảnh cũ
                        String oldFileName = oldImageName.replace("/images/", "");
                        File oldFile = new File(uploadDir + oldFileName);
                        if (oldFile.exists()) {
                            boolean isDeleted = oldFile.delete(); // Xóa tệp cũ
                            if (!isDeleted) {
                                throw new RuntimeException("Failed to delete old image file: " + oldFileName);
                            }
                        }
                    }
                } else {
                    dir.mkdirs(); // Tạo thư mục nếu chưa có
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
    public Page<DestinationAll> searchDestinations(String search, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by(Sort.Direction.DESC, "id")) // Thêm sorting ID DESC
        );
        Page<Destination> destinations = destinationRepository.searchDestinations(search, sortedPageable);

        return destinations.map(d -> {
            Continents continent = continentRepository.findById(d.getContinentId()).orElse(null);
            String continentName = (continent != null) ? continent.getContinentName() : "Unknown";
            Long continentId = (continent != null) ? continent.getContinentId() : 0;

            return new DestinationAll(
                    d.getId(),
                    d.getDestination(),
                    continentName,
                    d.getImageUrl(),
                    d.getDescription(),
                    d.getShow(),
                    continentId
            );
        });
    }
}
