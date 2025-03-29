package app.travelstride.Controller;

import app.travelstride.Config.CommonUpload;
import app.travelstride.Model.*;
import app.travelstride.Model.Jpa.*;
import app.travelstride.Model.dto.DestinationResponse;
import app.travelstride.Model.dto.HomePageResponse;
import app.travelstride.Service.BannerService;
import app.travelstride.Service.DestinationService;
import app.travelstride.Service.StylesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private BannerIamgeRepository BannerIamgeRepository;

    @Autowired
    private BannerService bannerService;
    @Autowired
    private TourImageRepository tourImageRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private BannerGroupRepository bannerRepository;
    @Autowired
    private StylesRepository stylesRepository;
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommonUpload commonUpload;
    @GetMapping("/banners")
    public List<BannerGroup> getBanners() {
        return bannerService.getAllBanners();
    }

    @GetMapping("/banners/{id}")
    public BannerGroup getBanners(@PathVariable Long id) {
        return bannerRepository.findById(id).orElse(new BannerGroup());
    }


    @PostMapping("/banners")
    public BannerGroup createBanner(@RequestParam("title") String title,
                                    @RequestParam(value = "subTitle", required = false) String subTitle,
                                    @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        BannerGroup bannerGroup = new BannerGroup();
        bannerGroup.setTitle(title);
        bannerGroup.setSubTitle(subTitle);

        List<BannerImage> imageList = new ArrayList<>();
        if (files != null) {
            int sequence = 1;
            for (MultipartFile file : files) {
                String imageUrl = commonUpload.saveImage(file);

                BannerImage image = new BannerImage();
                image.setImageUrl(imageUrl);
                image.setSequence(sequence++);
                image.setBannerGroup(bannerGroup);

                imageList.add(image);
            }
        }
        bannerGroup.setImages(imageList);

        return bannerService.saveBanner(bannerGroup);
    }

    @PutMapping("/banners/{id}")
    public BannerGroup updateBanner(@PathVariable Long id,
                                    @RequestParam("title") String title,
                                    @RequestParam(value = "subTitle", required = false) String subTitle,
                                    @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException, IOException {

        BannerGroup existing = bannerService.getBannerById(id);
        existing.setTitle(title);
        existing.setSubTitle(subTitle);
        BannerIamgeRepository.deleteAll(existing.getImages());
        deleteOldImages(existing);
        if (files != null) {
            int sequence = 1;
            for (MultipartFile file : files) {
                String imageUrl = commonUpload.saveImage(file);
                BannerImage image = new BannerImage();
                image.setImageUrl(imageUrl);
                image.setSequence(sequence++);
                image.setBannerGroup(existing);
                existing.getImages().add(image);
            }
        }

        return bannerService.saveBanner(existing);
    }

    @DeleteMapping("/banners/{id}")
    public void deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
    }
    @GetMapping
    public HomePageResponse getHomePage() {
        HomePageResponse response = new HomePageResponse();

        List<BannerGroup> banners = bannerRepository.findAll();
        List<Styles> styles = stylesRepository.findAll();
        List<Post> posts = postRepository.findByIsShow(true);
        List<DestinationResponse> destinations = destinationRepository.getDestinationWithTourCount();
        Map<String, Integer> destinationCount = countToursByDestination();
        Map<String, Object> findTrips = searchTours(1);
        response.setBanners(banners);
        response.setFindTrips(findTrips);
        response.setPots(posts);
        response.setStyles(styles);
        response.setDestinations(destinations);
        response.setDestinationCount(destinationCount);

        return response;
    }

    private Map<String, Integer> countToursByDestination() {
        List<Tour> tours = tourRepository.findAll();
        Map<String, Integer> destinationCount = new HashMap<>();

        for (Tour tour : tours) {
            if (tour.getDestinations() != null && !tour.getDestinations().isEmpty()) {
                String[] destinations = tour.getDestinations().split(",");
                for (String dest : destinations) {
                    String trimmedDest = dest.trim();
                    destinationCount.put(trimmedDest, destinationCount.getOrDefault(trimmedDest, 0) + 1);
                }
            }
        }
        return destinationCount;
    }
    public Map<String, Object> searchTours(Integer isTrending) {
        // Tìm các tour theo isTrending (0 hoặc 1), không phân trang
        List<Tour> tours = tourRepository.findByIsTrending(isTrending);

        // Lấy danh sách tourId
        List<Long> tourIds = tours.stream()
                .map(Tour::getId)
                .collect(Collectors.toList());

        // Lấy review summary
        List<Map<String, Object>> reviewSummaryList = reviewRepository.getReviewSummaryByTourIds(tourIds);

        // Lấy ảnh tour
        List<TourImage> imageEntities = tourImageRepository.findImagesByTourIds(tourIds);

        // Group reviews theo tourId
        Map<Long, Map<String, Object>> reviewMap = reviewSummaryList.stream()
                .collect(Collectors.toMap(
                        r -> (Long) r.get("tourId"),  // key là tourId
                        r -> r                       // value là map review
                ));

        // Group images theo tourId
        Map<Long, List<Map<String, Object>>> imageMap = new HashMap<>();
        for (TourImage img : imageEntities) {
            imageMap.computeIfAbsent(img.getTourId(), k -> new ArrayList<>()).add(Map.of(
                    "id", img.getId(),
                    "tourId", img.getTourId(),
                    "url", img.getUrl()
            ));
        }

        // Đóng gói từng tour kèm reviews và images
        List<Map<String, Object>> tourDataList = new ArrayList<>();
        for (Tour tour : tours) {
            Map<String, Object> tourData = new HashMap<>();
            tourData.put("tour", tour);
            tourData.put("images", imageMap.getOrDefault(tour.getId(), new ArrayList<>()));
            tourData.put("review", reviewMap.getOrDefault(tour.getId(), new HashMap<>()));
            tourDataList.add(tourData);
        }

        // Trả kết quả
        Map<String, Object> result = new HashMap<>();
        result.put("data", tourDataList);
        return result;
    }
    private void deleteOldImages(BannerGroup banner) {
        if (banner.getImages() != null && !banner.getImages().isEmpty()) {
            String uploadDir = "/home/user/Travel/BE/images/"; // Thay bằng đường dẫn thực tế

            for (BannerImage image : banner.getImages()) {
                String oldFileName = image.getImageUrl().replace("/images/", "");
                File oldFile = new File(uploadDir + oldFileName);

                if (oldFile.exists() && !oldFile.delete()) {
                    System.err.println("Failed to delete old image file: " + oldFileName);
                }
            }

            BannerIamgeRepository.deleteAll(banner.getImages()); // Xóa bản ghi ảnh trong DB
            banner.getImages().clear(); // Xóa danh sách ảnh cũ trong entity
        }
    }
}
