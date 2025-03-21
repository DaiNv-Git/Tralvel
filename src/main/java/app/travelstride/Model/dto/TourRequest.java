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
    private List<TourInterests> tourInterests;
    private Logistics logistics;
    private List<TourStyle> tourStyles;

    public TourRequest() {
    }

    public TourRequest(Tour tour, List<TourTheme> themes, List<TourTrending> trending, List<TourActivity> activities, List<TourInterests> tourInterests, Logistics logistics, List<TourStyle> tourStyles) {
        this.tour = tour;
        this.themes = themes;
        this.trending = trending;
        this.activities = activities;
        this.tourInterests = tourInterests;
        this.logistics = logistics;
        this.tourStyles = tourStyles;
    }

    public List<TourStyle> getTourStyles() {
        return tourStyles;
    }

    public void setTourStyles(List<TourStyle> tourStyles) {
        this.tourStyles = tourStyles;
    }

    public List<TourInterests> getTourInterests() {
        return tourInterests;
    }

    public void setTourInterests(List<TourInterests> tourInterests) {
        this.tourInterests = tourInterests;
    }

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

