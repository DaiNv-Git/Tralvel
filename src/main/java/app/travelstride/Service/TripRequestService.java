package app.travelstride.Service;

import app.travelstride.Model.Jpa.TripRequestRepository;
import app.travelstride.Model.Jpa.impl.TripRequestSpecification;
import app.travelstride.Model.TripRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TripRequestService {

    @Autowired
    private TripRequestRepository repository;

    public TripRequest create(TripRequest trip) {
        return repository.save(trip);
    }

    public TripRequest update(Long id, TripRequest trip) {
        trip.setId(id);
        return repository.save(trip);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<TripRequest> search(String name, String phone, LocalDate from, LocalDate to, Pageable pageable) {
        Specification<TripRequest> spec = TripRequestSpecification.filter(name, phone, from, to);
        return repository.findAll(spec, pageable);
    }

    public Optional<TripRequest> getById(Long id) {
        return repository.findById(id);
    }
}
