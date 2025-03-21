package app.travelstride.Model.dto;

import app.travelstride.Model.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class TourDetailResponse {
    private Tour tour;
    private List<TourTheme> themes;
    private List<TourTrending> trending;
    private List<Review> reviews;
    private Logistics logistics;
}
