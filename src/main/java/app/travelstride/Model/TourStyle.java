package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "tour_style")
@Data
public class TourStyle {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long tourId;
        private Long styleId;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

         public Long getTourId() {
                return tourId;
        }

        public void setTourId(Long tourId) {
                this.tourId = tourId;
        }

        public Long getStyleId() {
                return styleId;
        }

        public void setStyleId(Long styleId) {
                this.styleId = styleId;
        }

        public TourStyle() {
        }

        public TourStyle(Long id, Long tourId, Long styleId) {
                this.id = id;
                this.tourId = tourId;
                this.styleId = styleId;
        }

}
