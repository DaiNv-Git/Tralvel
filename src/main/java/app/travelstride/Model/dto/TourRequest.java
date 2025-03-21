package app.travelstride.Model.dto;

import app.travelstride.Model.*;
import lombok.Data;

import java.util.List;

@Data
public class TourRequest {
    private Tour tour;
    private List<TourTheme> themes;
    private List<TourTrending> trending;
    private List<TourActivity> activities;
    private Logistics logistics;

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public List<TourTheme> getThemes() {
        return themes;
    }

    public void setThemes(List<TourTheme> themes) {
        this.themes = themes;
    }

    public List<TourTrending> getTrending() {
        return trending;
    }

    public void setTrending(List<TourTrending> trending) {
        this.trending = trending;
    }

    public List<TourActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<TourActivity> activities) {
        this.activities = activities;
    }


    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }
}

