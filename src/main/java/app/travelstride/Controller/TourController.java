package app.travelstride.Controller;

import app.travelstride.Model.Jpa.*;
import app.travelstride.Model.dto.*;
import app.travelstride.Service.TourService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
import java.util.function.Consumer;
import java.util.function.Function;
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
            @RequestBody TourCreateRequest request,
            @RequestPart(required = false) MultipartFile[] images
    ) throws IOException {
        Tour savedTour = tourRepository.save(request.getTour());

        if (request.getLogistics() != null) {
            logisticsRepository.save(request.getLogistics());
        }

        // ✅ Upload và lưu ảnh
        if (images != null && images.length > 0) {
            List<TourImage> imageList = new ArrayList<>();
            for (MultipartFile file : images) {
                String url = commonUpload.saveImage(file);
                imageList.add(new TourImage(null, savedTour.getId(), url));
            }
            tourImageRepository.saveAll(imageList);
        }


        if (request.getActivityIds() != null) {
            List<TourActivity> activities = request.getActivityIds().stream()
                    .map(aid -> new TourActivity(null, savedTour.getId(), aid))
                    .collect(Collectors.toList());
            tourActivityRepository.saveAll(activities);
        }


        if (request.getDestinationIds() != null) {
            List<Destination> destinations = destinationRepository.findAllById(request.getDestinationIds());
            List<TourDestination> tourDestinations = destinations.stream()
                    .map(destination -> new TourDestination(savedTour, destination))
                    .collect(Collectors.toList());
            tourDestinationRepository.saveAll(tourDestinations);
        }

        if (request.getInterestIds() != null) {
            List<TourInterests> interests = request.getInterestIds().stream()
                    .map(iid -> new TourInterests(null, iid, savedTour.getId()))
                    .collect(Collectors.toList());
            tourInterestsRepository.saveAll(interests);
        }


        if (request.getStyleIds() != null) {
            List<TourStyle> styles = request.getStyleIds().stream()
                    .map(sid -> new TourStyle(null, savedTour.getId(), sid))
                    .collect(Collectors.toList());
            tourStyleRepository.saveAll(styles);
        }

        if (request.getThemeIds() != null && request.getThemes() != null) {
            List<TourTheme> themeList = new ArrayList<>();
            for (int i = 0; i < request.getThemeIds().size(); i++) {
                themeList.add(new TourTheme(null, savedTour.getId(), request.getThemes().get(i), request.getThemeIds().get(i)));
            }
            tourThemeRepository.saveAll(themeList);
        }
        return "Create success!";
    }

    @GetMapping("/searchAdmin")
    public ResponseEntity<Map<String, Object>> searchTour(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tour> tourPage;

        if (name == null || name.isEmpty()) {
            tourPage = tourRepository.findAll(pageable);
        } else {
            tourPage = tourRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("tours", tourPage.getContent());
        response.put("currentPage", tourPage.getNumber());
        response.put("totalPages", tourPage.getTotalPages());
        response.put("totalItems", tourPage.getTotalElements());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTour(
            @PathVariable Long id,
            @RequestPart TourCreateRequest tourUpdateDTO,
            @RequestPart(required = false) MultipartFile[] images
    ) throws IOException {

        // ✅ Kiểm tra tour tồn tại
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));

        // ✅ Cập nhật thông tin tour
        Tour tour = tourUpdateDTO.getTour();
        existingTour.setName(tour.getName());
        existingTour.setTripId(tour.getTripId());
        existingTour.setLodgingLevel(tour.getLodgingLevel());
        existingTour.setTotalDay(tour.getTotalDay());
        existingTour.setPrice(tour.getPrice());
        existingTour.setOldPrice(tour.getOldPrice());
        existingTour.setTripType(tour.getTripType());
        existingTour.setPhysicalLevel(tour.getPhysicalLevel());
        existingTour.setTripPace(tour.getTripPace());
        existingTour.setHighlights(tour.getHighlights());
        existingTour.setTripAbout(tour.getTripAbout());
        existingTour.setFlyAndTransport(tour.getFlyAndTransport());
        existingTour.setStartCity(tour.getStartCity());
        existingTour.setEndCity(tour.getEndCity());
        existingTour.setItineraryFocus(tour.getItineraryFocus());
        existingTour.setGroupSize(tour.getGroupSize());
        existingTour.setAgeRange(tour.getAgeRange());
        existingTour.setMinGroupSize(tour.getMinGroupSize());
        existingTour.setMaxGroupSize(tour.getMaxGroupSize());
        existingTour.setAttractions(tour.getAttractions());
        existingTour.setDestinations(tour.getDestinations());
        existingTour.setIsTrending(tour.getIsTrending());

        tourRepository.save(existingTour);

        // ✅ Update logistics
        if (tourUpdateDTO.getLogistics() != null) {
            Logistics logistics = tourUpdateDTO.getLogistics();
            logistics.setTourId(id);
            logisticsRepository.save(logistics);
        }

        // ✅ Update Images: Xoá cũ -> Thêm mới
        if (images != null && images.length > 0) {
            tourImageRepository.deleteByTourId(id);
            List<TourImage> imageList = new ArrayList<>();
            for (MultipartFile file : images) {
                String url = commonUpload.saveImage(file);
                imageList.add(new TourImage(null, id, url));
            }
            tourImageRepository.saveAll(imageList);
        }


        updateTourRelations(id, tourUpdateDTO);

        return ResponseEntity.ok("Tour updated successfully");
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
            int countOverall = 0, countValue = 0, countGuide = 0, countActivities = 0, countLodging = 0, countTransportation = 0, countMeals = 0;

            // Biến đếm số lượng đánh giá từ 1 sao đến 5 sao
            int rating1 = 0, rating2 = 0, rating3 = 0, rating4 = 0, rating5 = 0, ratingUnrated = 0;

            for (Review review : reviews) {
                if (review.getOverallRating() > 0) {
                    overall += review.getOverallRating();
                    countOverall++;

                    // Phân loại đánh giá theo số sao
                    int stars = (int) Math.round(review.getOverallRating());
                    switch (stars) {
                        case 1: rating1++; break;
                        case 2: rating2++; break;
                        case 3: rating3++; break;
                        case 4: rating4++; break;
                        case 5: rating5++; break;
                        default: ratingUnrated++; break;
                    }
                }
                if (review.getValueRating() > 0) {
                    value += review.getValueRating();
                    countValue++;
                }
                if (review.getGuideRating() > 0) {
                    guide += review.getGuideRating();
                    countGuide++;
                }
                if (review.getActivitiesRating() > 0) {
                    activities += review.getActivitiesRating();
                    countActivities++;
                }
                if (review.getLodgingRating() > 0) {
                    lodging += review.getLodgingRating();
                    countLodging++;
                }
                if (review.getTransportationRating() > 0) {
                    transportation += review.getTransportationRating();
                    countTransportation++;
                }
                if (review.getMealsRating() > 0) {
                    meals += review.getMealsRating();
                    countMeals++;
                }
            }

            int total = reviews.size();
            averageRatings.put("overallRatingAvg", countOverall > 0 ? overall / countOverall : 0);
            averageRatings.put("valueRatingAvg", countValue > 0 ? value / countValue : 0);
            averageRatings.put("guideRatingAvg", countGuide > 0 ? guide / countGuide : 0);
            averageRatings.put("activitiesRatingAvg", countActivities > 0 ? activities / countActivities : 0);
            averageRatings.put("lodgingRatingAvg", countLodging > 0 ? lodging / countLodging : 0);
            averageRatings.put("transportationRatingAvg", countTransportation > 0 ? transportation / countTransportation : 0);
            averageRatings.put("mealsRatingAvg", countMeals > 0 ? meals / countMeals : 0);
            averageRatings.put("totalReviews", total);

            // Thêm số lượng đánh giá từng mức sao
            averageRatings.put("rating1Star", rating1);
            averageRatings.put("rating2Star", rating2);
            averageRatings.put("rating3Star", rating3);
            averageRatings.put("rating4Star", rating4);
            averageRatings.put("rating5Star", rating5);
            averageRatings.put("ratingUnrated", ratingUnrated);
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

    @GetMapping("/adminDetail/{id}")
    public Map<String, Object> getDetail(@PathVariable Long id) {
        Map<String, Object> data = new HashMap<>();

        Optional<Tour> tourOpt = tourRepository.findById(id);
        if (tourOpt.isEmpty()) {
            data.put("message", "Tour not found");
            return data;
        }

        Tour tour = tourOpt.get();

        // Lấy thông tin từ các bảng liên quan
        Map<String, Object> tourData = new HashMap<>();
        tourData.put("tour", tour);
        tourData.put("images", tourImageRepository.findImagesByTourIds(Collections.singletonList(id)));
        tourData.put("themes", themeRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("activities", activityRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("interest", interestsRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("styles", stylesRepository.findByTourIds(Collections.singletonList(id)));
        tourData.put("logistics", logisticsRepository.findByTourId(id));

        // Lấy danh sách điểm đến (Destinations)
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
    private void updateTourRelations(Long tourId, TourCreateRequest dto) {
        updateRelation(tourId, dto.getActivityIds(), tourActivityRepository::deleteByTourId,
                (id) -> new TourActivity(null, tourId, id), tourActivityRepository);

        updateRelation(tourId, dto.getDestinationIds(), tourDestinationRepository::deleteByTourId,
                (id) -> new TourDestination(null, tourId, id), tourDestinationRepository);

        updateRelation(tourId, dto.getInterestIds(), tourInterestsRepository::deleteByTourId,
                (id) -> new TourInterests(null, tourId, id), tourInterestsRepository);

        updateRelation(tourId, dto.getStyleIds(), tourStyleRepository::deleteByTourId,
                (id) -> new TourStyle(null, tourId, id), tourStyleRepository);

        updateRelation(tourId, dto.getThemeIds(), tourThemeRepository::deleteByTourId,
                (id) -> new TourTheme(null, tourId, id), tourThemeRepository);
    }

    private <T> void updateRelation(
            Long tourId,
            List<Long> ids,
            Consumer<Long> deleteFunction,
            Function<Long, T> createFunction,
            JpaRepository<T, Long> repository
    ) {
        if (ids != null) {
            deleteFunction.accept(tourId);
            List<T> entities = ids.stream()
                    .map(createFunction)
                    .collect(Collectors.toList());
            repository.saveAll(entities);
        }
    }


}

