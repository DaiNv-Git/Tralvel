package app.travelstride.Controller;

import app.travelstride.Model.Jpa.*;
import app.travelstride.Model.dto.TourDetailResponse;
import app.travelstride.Model.dto.TourRequest;
import app.travelstride.Model.dto.TourResponse;
import app.travelstride.Service.TourService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

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
    @Autowired
    private TourInterestsRepository tourInterestsRepository;
    @Autowired
    private TourStyleRepository tourStyleRepository;
    @Autowired
    private TourService tourService;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private TredingRepository tredingRepository;

    @Autowired
    private ActivityRepository activityRepository;


    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private StylesRepository stylesRepository;


    @PostMapping(value = "/createFull", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public String createFullTour(
            @RequestPart("tourRequest") String tourRequestStr,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        TourRequest request = objectMapper.readValue(tourRequestStr, TourRequest.class);
        request.getTour().setId(null);

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
        saveStyles(request.getTourStyles(),tourId);
        if (request.getLogistics() != null) {
            request.getLogistics().setTourId(tourId);
            request.getLogistics().setId(null);
            logisticsRepository.save(request.getLogistics());
        }

        return "Create Tour Full Success with ID: " + tourId;
    }



    @PutMapping("/updateFull/{id}")
    public String updateFullTour(@PathVariable Long id,
                                 @RequestBody TourRequest request) {
        Optional<Tour> optionalTour = tourRepository.findById(id);
        if (optionalTour.isEmpty()) return "Tour not found";

        Tour existingTour = optionalTour.get();
        existingTour.setName(request.getTour().getName());
        existingTour.setTripId(request.getTour().getTripId());

        tourRepository.save(existingTour);


        tourThemeRepository.deleteByTourId(id);
        saveThemes(request.getThemes(), id);
        tourInterestsRepository.deleteByTourId(id);
        saveInterests(request.getTourInterests(),id);
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
    @Transactional
    public String deleteFullTour(@PathVariable Long id) {
        tourImageRepository.deleteAllByTourId(id);
        tourThemeRepository.deleteByTourId(id);
        tourTrendingRepository.deleteByTourId(id);
        tourActivityRepository.deleteByTourId(id);
        reviewRepository.deleteByTourId(id);
        logisticsRepository.deleteByTourId(id);
        tourStyleRepository.deleteByTourId(id);
        tourInterestsRepository.deleteByTourId(id);
        tourRepository.deleteById(id);
        return "Delete Tour Full Success";
    }


    @GetMapping("/getFull/{id}")
    public Map<String, Object> getFullTour(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        result.put("tour", tourRepository.findById(id));
        result.put("images", tourImageRepository.findByTourId(id));
        result.put("themes", themeRepository.findByTourIds(Collections.singletonList(id)));
        result.put("trending", tredingRepository.findByTourIds(Collections.singletonList(id)));
        result.put("activities", activityRepository.findByTourIds(Collections.singletonList(id)));
        result.put("interest", interestsRepository.findByTourIds(Collections.singletonList(id)));
        result.put("styles", stylesRepository.findByTourIds(Collections.singletonList(id)));
        result.put("images", tourImageRepository.findImagesByTourIds(Collections.singletonList(id)));
        result.put("reviews", reviewRepository.findByTourId(id));
        result.put("logistics", logisticsRepository.findByTourId(id));
        return result;
    }

    // ✅ Hàm lưu file ảnh
    private String saveImage(MultipartFile file) {
        try {
            String uploadDir = "uploads/images/";
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
        if (themes != null && !themes.isEmpty()) {
            List<TourTheme> themeList = themes.stream()
                    .peek(theme -> {
                        theme.setTourId(tourId);
                        theme.setId(null); // Set themeId = null để tránh update, đảm bảo insert mới
                    })
                    .collect(Collectors.toList());
            tourThemeRepository.saveAll(themeList);
        }
    }

    private void saveStyles(List<TourStyle> styles, Long tourId) {
        if (styles != null && !styles.isEmpty()) {
            List<TourStyle> styleList = styles.stream()
                    .peek(style -> {
                        style.setTourId(tourId);
                        style.setId(null);
                    })
                    .collect(Collectors.toList());
            tourStyleRepository.saveAll(styleList);
        }
    }
    private void updateStyles(List<TourStyle> styles, Long tourId) {
        // Xóa hết style cũ theo tourId
        tourStyleRepository.deleteByTourId(tourId);

        // Thêm mới nếu có
        if (styles != null && !styles.isEmpty()) {
            List<TourStyle> styleList = styles.stream()
                    .peek(style -> {
                        style.setTourId(tourId);
                        style.setId(null); // Đảm bảo insert mới
                    })
                    .collect(Collectors.toList());
            tourStyleRepository.saveAll(styleList);
        }
    }


    private void saveInterests(List<TourInterests> interests, Long tourId) {
        if (interests != null && !interests.isEmpty()) {
            List<TourInterests> interestList = interests.stream()
                    .peek(interest -> {
                        interest.setId(null); // Đảm bảo thêm mới
                        interest.setTourId(tourId);
                    })
                    .collect(Collectors.toList());
            tourInterestsRepository.saveAll(interestList);
        }
    }

    private void saveTrending(List<TourTrending> trending, Long tourId) {
        if (trending != null && !trending.isEmpty()) {
            List<TourTrending> trendList = trending.stream()
                    .peek(trend -> {
                        trend.setTourId(tourId);
                        trend.setId(null); // Reset id để insert mới
                    })
                    .collect(Collectors.toList());
            tourTrendingRepository.saveAll(trendList);
        }
    }


    private void saveActivities(List<TourActivity> activities, Long tourId) {
        if (activities != null && !activities.isEmpty()) {
            List<TourActivity> activityList = activities.stream()
                    .peek(activity -> {
                        activity.setTourId(tourId);
                        activity.setId(null); // Reset ID để insert mới
                    })
                    .collect(Collectors.toList());
            tourActivityRepository.saveAll(activityList);
        }
    }
    private void updateInterests(List<TourInterests> interests, Long tourId) {
        // Xóa các bản ghi cũ theo tourId
        tourInterestsRepository.deleteByTourId(tourId);
        // Lưu lại danh sách mới
        saveInterests(interests, tourId);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTours(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tour> tours = tourRepository.searchByKeyword(keyword, pageable);

        List<Long> tourIds = tours.getContent().stream().map(Tour::getId).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("tours", tours.getContent());
        result.put("images", tourImageRepository.findImagesByTourIds(tourIds));
        result.put("currentPage", tours.getNumber());
        result.put("totalItems", tours.getTotalElements());
        result.put("totalPages", tours.getTotalPages());

        return ResponseEntity.ok(result);
    }
    @GetMapping("/trending")
    public ResponseEntity<List<Tour>> getTrendingTours() {
        List<Tour> trendingTours = tourService.getTrendingTours();
        return ResponseEntity.ok(trendingTours);
    }
}

