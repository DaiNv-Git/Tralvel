package app.travelstride.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tour_destination")
public class TourDestination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    @JsonIgnore
    private Destination destination;

    // Nếu sau này muốn thêm cột như "visit_order" hay "note"
    // private Integer visitOrder;

    // Constructors
    public TourDestination() {}
    public TourDestination(Tour tour, Destination destination) {
        this.tour = tour;
        this.destination = destination;
    }
    public TourDestination(Long id, Tour tour, Destination destination) {
        this.id = id;
        this.tour = tour;
        this.destination = destination;
    }
    public TourDestination(Object o, Long id, Long did) {
    }


    public void Destination(Long id) {
        this.id = id;
    }


  

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
