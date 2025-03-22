package app.travelstride.Service;

import app.travelstride.Model.Jpa.TourRepository;
import app.travelstride.Model.Tour;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {
    @Autowired
    TourRepository tourRepository;

    public Page<Tour> searchTour(Long tourId, String tourName, String themeName, String trendingName,
                                 String activityName, String interestName, String attractions, String destinations,
                                 int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return tourRepository.searchAll(tourId, tourName, themeName, trendingName, activityName, interestName, attractions, destinations, pageable);
    }


    public List<Tour> getTrendingTours() {
        return tourRepository.findByIsTrending(1);  // 1 nghĩa là true
    }
}
