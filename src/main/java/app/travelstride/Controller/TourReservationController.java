package app.travelstride.Controller;

import app.travelstride.Model.Jpa.TourRepository;
import app.travelstride.Model.Jpa.TourReservationRepository;
import app.travelstride.Model.TourReservation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tour-reservations")
public class TourReservationController {
    @Autowired
    TourReservationRepository reservationRepository;
    @Autowired
    private  TourRepository tourRepository;
    @PostMapping
    public TourReservation createReservation(@RequestBody TourReservation reservation) {
        // Check tourId valid
        if (reservation.getTour() != null && reservation.getTour().getId() != null) {
            reservation.setTour(tourRepository.findById(reservation.getTour().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Tour not found")));
        }
        return reservationRepository.save(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourReservation> updateReservation(@PathVariable Long id, @RequestBody TourReservation updatedReservation) {
        Optional<TourReservation> existingReservation = reservationRepository.findById(id);
        if (existingReservation.isPresent()) {
            updatedReservation.setId(id);
            TourReservation savedReservation = reservationRepository.save(updatedReservation);
            return ResponseEntity.ok(savedReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found");
        }
        reservationRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public TourReservation getReservationById(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }
    @GetMapping("/search")
    public Page<TourReservation> searchReservations(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String tourName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return reservationRepository.searchReservations(name, mobile, tourName, pageable);
    }

}
