package app.travelstride.Model.dto;

import app.travelstride.Model.*;
import lombok.Data;

import java.util.List;

@Data
public class TourResponse {
    private Tour tour;
    private List<TourImage> images;
}
