package app.travelstride.Controller;

import app.travelstride.Model.Jpa.*;
import app.travelstride.Model.dto.TourDetailResponse;
import app.travelstride.Model.dto.TourRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.travelstride.Model.*;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourImageRepository tourImageRepository;
    @Autowired
    private TourThemeRepository tourThemeRepository;
    @Autowired
    private TourTrendingRepository tourTrendingRepository;
    @Autowired
    private TourActivityRepository tourActivityRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private LogisticsRepository logisticsRepository;

    @PostMapping(value = "/createFull", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public String createFullTour(
            @RequestPart("tourRequest") String tourRequestStr,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        TourRequest request = objectMapper.readValue(tourRequestStr, TourRequest.class);

        // Xử lý request như cũ
        Tour savedTour = tourRepository.save(request.getTour());
        Long tourId = savedTour.getId();

        if (images != null && !images.isEmpty()) {
            images.forEach(file -> {
                String filePath = saveImage(file);
                TourImage img = new TourImage();
                img.setTourId(tourId);
                img.setUrl(filePath);
                tourImageRepository.save(img);
            });
        }

        saveThemes(request.getThemes(), tourId);
        saveTrending(request.getTrending(), tourId);
        saveActivities(request.getActivities(), tourId);

        if (request.getLogistics() != null) {
            request.getLogistics().setTourId(tourId);
            logisticsRepository.save(request.getLogistics());
        }

        return "Create Tour Full Success with ID: " + tourId;
    }


    // ✅ UPDATE Tour Full
    @PutMapping("/updateFull/{id}")
    public String updateFullTour(@PathVariable Long id,
                                 @RequestBody TourRequest request) {
        Optional<Tour> optionalTour = tourRepository.findById(id);
        if (optionalTour.isEmpty()) return "Tour not found";

        // Cập nhật Tour
        Tour existingTour = optionalTour.get();
        existingTour.setName(request.getTour().getName());
        existingTour.setTripId(request.getTour().getTripId());
        // ... (Các field khác cần update)
        tourRepository.save(existingTour);

        // Xóa hết cũ rồi lưu mới các bảng liên quan (hoặc viết update riêng)
        tourThemeRepository.deleteByTourId(id);
        saveThemes(request.getThemes(), id);

        tourTrendingRepository.deleteByTourId(id);
        saveTrending(request.getTrending(), id);

        tourActivityRepository.deleteByTourId(id);
        saveActivities(request.getActivities(), id);

        reviewRepository.deleteByTourId(id);


        logisticsRepository.deleteByTourId(id);
        if (request.getLogistics() != null) {
            request.getLogistics().setTourId(id);
            logisticsRepository.save(request.getLogistics());
        }

        return "Update Tour Full Success";
    }

    // ✅ DELETE Tour Full
    @DeleteMapping("/deleteFull/{id}")
    public String deleteFullTour(@PathVariable Long id) {
        // Xóa các bảng liên quan
        tourImageRepository.deleteAllByTourId(id);
        tourThemeRepository.deleteByTourId(id);
        tourTrendingRepository.deleteByTourId(id);
        tourActivityRepository.deleteByTourId(id);
        reviewRepository.deleteByTourId(id);
        logisticsRepository.deleteByTourId(id);

        // Xóa Tour
        tourRepository.deleteById(id);

        return "Delete Tour Full Success";
    }


    @GetMapping("/getFull/{id}")
    public Map<String, Object> getFullTour(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("tour", tourRepository.findById(id));
        result.put("images", tourImageRepository.findByTourId(id));
        result.put("themes", tourThemeRepository.findByTourId(id));
        result.put("trending", tourTrendingRepository.findByTourId(id));
        result.put("activities", tourActivityRepository.findAllByTourId(id));
        result.put("reviews", reviewRepository.findByTourId(id));
        result.put("logistics", logisticsRepository.findByTourId(id));
        return result;
    }

    // ✅ Hàm lưu file ảnh
    private String saveImage(MultipartFile file) {
        try {
            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
    }

    // ✅ Hàm lưu các bảng phụ
    private void saveThemes(List<TourTheme> themes, Long tourId) {
        if (themes != null) {
            themes.forEach(theme -> {
                theme.setTourId(tourId);
                tourThemeRepository.save(theme);
            });
        }
    }

    private void saveTrending(List<TourTrending> trending, Long tourId) {
        if (trending != null) {
            trending.forEach(trend -> {
                trend.setTourId(tourId);
                tourTrendingRepository.save(trend);
            });
        }
    }

    private void saveActivities(List<TourActivity> activities, Long tourId) {
        if (activities != null) {
            activities.forEach(activity -> {
                activity.setTourId(tourId);
                tourActivityRepository.save(activity);
            });
        }
    }

    private void saveReviews(List<Review> reviews, Long tourId) {
        if (reviews != null) {
            reviews.forEach(review -> {
                review.setTourId(tourId);
                reviewRepository.save(review);
            });
        }
    }
}

