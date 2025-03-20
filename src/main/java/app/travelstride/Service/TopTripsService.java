package app.travelstride.Service;

import app.travelstride.Model.Jpa.TopTripsRepository;
import app.travelstride.Model.TopTrips;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopTripsService {
    @Autowired
    private  TopTripsRepository topTripsRepository;

    public List<TopTrips> getAll() {
        return topTripsRepository.findAll();
    }

    public TopTrips create(TopTrips topTrips) {
        return topTripsRepository.save(topTrips);
    }

    public TopTrips update(Long id, TopTrips topTrips) {
        TopTrips old = topTripsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Top Trip not found"));
        old.setName(topTrips.getName());
        return topTripsRepository.save(old);
    }

    public void delete(Long id) {
        topTripsRepository.deleteById(id);
    }


}
