package app.travelstride.Controller;

import app.travelstride.Model.Jpa.*;
import app.travelstride.Model.dto.DestinationDTO1;
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
import org.springframework.http.HttpStatus;
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
import app.travelstride.Config.CommonUpload;

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
    private  CommonUpload commonUpload;


    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private StylesRepository stylesRepository;
    @Autowired
    private DestinationRepository destinationRepository ;

    @Autowired
    private TourDestinationRepository tourDestinationRepository ;


    @PostMapping("/create")
    public String createTour(
            @RequestPart Tour tour,
            @RequestPart(required = false) MultipartFile[] images,
            @RequestParam(required = false) List<Long> activityIds,
            @RequestParam(required = false) List<Long> destinationIds,
            @RequestParam(required = false) List<Long> interestIds,
            @RequestParam(required = false) List<Long> styleIds,
            @RequestParam(required = false) List<Long> themeIds,
            @RequestParam(required = false) List<String> themes
    ) throws IOException {
        Tour savedTour = tourRepository.save(tour);

        // ✅ Upload và lưu ảnh
        if (images != null && images.length > 0) {
            List<TourImage> imageList = new ArrayList<>();
            for (MultipartFile file : images) {
                String url = commonUpload.saveImage(file);
                imageList.add(new TourImage(null, savedTour.getId(), url));
            }
            tourImageRepository.saveAll(imageList);
        }

        // ✅ Hoạt động
        if (activityIds != null) {
            List<TourActivity> activities = activityIds.stream()
                    .map(aid -> new TourActivity(null, savedTour.getId(), aid))
                    .collect(Collectors.toList());
            tourActivityRepository.saveAll(activities);
        }

        // ✅ Destination
        if (destinationIds != null) {
            List<TourDestination> destinations = destinationIds.stream()
                    .map(did -> new TourDestination(null, savedTour, new Destination(did)))
                    .collect(Collectors.toList());
            tourDestinationRepository.saveAll(destinations);
        }

        // ✅ Interest
        if (interestIds != null) {
            List<TourInterests> interests = interestIds.stream()
                    .map(iid -> new TourInterests(null, iid, savedTour.getId()))
                    .collect(Collectors.toList());
            tourInterestsRepository.saveAll(interests);
        }

        // ✅ Style
        if (styleIds != null) {
            List<TourStyle> styles = styleIds.stream()
                    .map(sid -> new TourStyle(null, savedTour.getId(), sid))
                    .collect(Collectors.toList());
            tourStyleRepository.saveAll(styles);
        }

        // ✅ Theme
        if (themeIds != null && themes != null) {
            List<TourTheme> themeList = new ArrayList<>();
            for (int i = 0; i < themeIds.size(); i++) {
                themeList.add(new TourTheme(null, savedTour.getId(), themes.get(i), themeIds.get(i)));
            }
            tourThemeRepository.saveAll(themeList);
        }

        return "Create success!";
    }
    @DeleteMapping("/tour/{id}")
    @Transactional
    public ResponseEntity<String> deleteTour(@PathVariable Long id) {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found");
        }

        // Xóa các bảng liên kết trước
        tourImageRepository.deleteByTourId(id);
        tourThemeRepository.deleteByTourId(id);
        tourActivityRepository.deleteByTourId(id);
        tourInterestsRepository.deleteByTourId(id);
        tourStyleRepository.deleteByTourId(id);
        tourDestinationRepository.deleteByTourId(id);
        logisticsRepository.deleteByTourId(id);
        reviewRepository.deleteByTourId(id);
        tourTrendingRepository.deleteByTourId(id);

        // Xóa tour
        tourRepository.deleteById(id);

        return ResponseEntity.ok("Tour deleted successfully");
    }
    @PutMapping("/tour/{id}")
    @Transactional
    public ResponseEntity<?> updateTour(
            @PathVariable Long id,
            @RequestPart("tour") Tour updatedTour,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        Optional<Tour> tourOptional = tourRepository.findById(id);
        if (tourOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tour not found");
        }

        Tour tour = tourOptional.get();

        // Update field cơ bản
        tour.setName(updatedTour.getName());
        tour.setTripAbout(updatedTour.getTripAbout());
        tour.setTotalDay(updatedTour.getTotalDay());
        // ... thêm các field khác
        tourRepository.save(tour);

        // Xử lý update hình ảnh (Xóa hết ảnh cũ, thêm mới)
        if (images != null && !images.isEmpty()) {
            tourImageRepository.deleteByTourId(id);  // Xóa hết ảnh cũ

            List<TourImage> tourImageList = new ArrayList<>();
            for (MultipartFile file : images) {
                try {
                    String url = commonUpload.saveImage(file);
                    TourImage tourImage = new TourImage();
                    tourImage.setTourId(id);
                    tourImage.setUrl(url);
                    tourImageList.add(tourImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tourImageRepository.saveAll(tourImageList); 
        }
        

        return ResponseEntity.ok("Tour updated successfully");
    }


    @GetMapping("/getFull/{id}")
    public Map<String, Object> getFullTour(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();

        Optional<Tour> tourOpt = tourRepository.findById(id);
        if (tourOpt.isEmpty()) {
            data.put("message", "Tour not found");
            return data;
        }

        Tour tour = tourOpt.get();
        List<Review> reviews = reviewRepository.findByTourId(id);

        Map<String, Object> averageRatings = new HashMap<>();
        if (!reviews.isEmpty()) {
            double overall = 0, value = 0, guide = 0, activities = 0, lodging = 0, transportation = 0, meals = 0;
            for (Review review : reviews) {
                overall += review.getOverallRating();
                value += review.getValueRating();
                guide += review.getGuideRating();
                activities += review.getActivitiesRating();
                lodging += review.getLodgingRating();
                transportation += review.getTransportationRating();
                meals += review.getMealsRating();
            }
            int total = reviews.size();
            averageRatings.put("overallRatingAvg", overall / total);
            averageRatings.put("valueRatingAvg", value / total);
            averageRatings.put("guideRatingAvg", guide / total);
            averageRatings.put("activitiesRatingAvg", activities / total);
            averageRatings.put("lodgingRatingAvg", lodging / total);
            averageRatings.put("transportationRatingAvg", transportation / total);
            averageRatings.put("mealsRatingAvg", meals / total);
            averageRatings.put("totalReviews", total);
        } else {
            averageRatings.put("message", "No reviews available");
        }

        Map<String, Object> tourData = new HashMap<>();
        tourData.put("tour", tour);
        tourData.put("images", tourImageRepository.findImagesByTourIds(Collections.singletonList(id)));
        tourData.put("themes", themeRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("activities", activityRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("interest", interestsRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("styles", stylesRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("reviews", reviews);
        tourData.put("averageRatings", averageRatings);
        tourData.put("logistics", logisticsRepository.findByTourId(id));
        tourData.put("destinations", destinationRepository.findByTourId(id));
        List<Destination> destinations = destinationRepository.findByTourId(id);
        List<DestinationDTO1> destinationDTOs = destinations.stream()
                .map(d -> new DestinationDTO1(
                        d.getId(),
                        d.getDestination(),
                        d.getContinentId(),
                        d.getImageUrl(),
                        d.getDescription()
                      
                )).collect(Collectors.toList());
        tourData.put("destinations", destinationDTOs);

        data.put("tourData", tourData);
        return data;
    }
    

    @GetMapping("/search")
    public ResponseEntity<?> searchTours(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Gọi đúng repository sử dụng @Query bạn đã viết
        Page<Tour> tours = tourRepository.searchByKeyword(keyword, pageable);

        if (tours.isEmpty()) {
            Map<String, Object> emptyResult = new HashMap<>();
            emptyResult.put("data", Collections.emptyMap());
            emptyResult.put("currentPage", page);
            emptyResult.put("totalItems", 0);
            emptyResult.put("totalPages", 0);
            return ResponseEntity.ok(emptyResult);
        }

        // Lấy ra danh sách tourId
        List<Long> tourIds = tours.getContent().stream().map(Tour::getId).toList();

        // Lấy hình ảnh theo list tourId
        Map<Long, List<String>> imagesMap = tourImageRepository.findImagesByTourIds(tourIds)
                .stream()
                .collect(Collectors.groupingBy(
                        TourImage::getTourId,
                        Collectors.mapping(TourImage::getUrl, Collectors.toList())
                ));

        // Lấy review summary theo list tourId
        Map<Long, Map<String, Object>> reviewsMap = reviewRepository.getReviewSummaryByTourIds(tourIds)
                .stream()
                .filter(r -> r.get("tourId") != null)
                .collect(Collectors.toMap(
                        r -> ((Number) r.get("tourId")).longValue(),
                        r -> r
                ));

        // Gom group theo tripType
        Map<String, List<Map<String, Object>>> groupedByTripType = new HashMap<>();
        for (Tour tour : tours.getContent()) {
            Map<String, Object> tourData = new HashMap<>();
            tourData.put("tourInfo", tour);
            tourData.put("images", imagesMap.getOrDefault(tour.getId(), new ArrayList<>()));
            tourData.put("reviewSummary", reviewsMap.getOrDefault(tour.getId(), new HashMap<>()));

            String tripType = tour.getTripType() != null ? tour.getTripType() : "Unknown";
            groupedByTripType.computeIfAbsent(tripType, k -> new ArrayList<>()).add(tourData);
        }

        // Build response
        Map<String, Object> result = new HashMap<>();
        result.put("data", groupedByTripType);
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

