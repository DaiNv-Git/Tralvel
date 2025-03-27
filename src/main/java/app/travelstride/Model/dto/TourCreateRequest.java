package app.travelstride.Model.dto;

import app.travelstride.Model.Logistics;
import app.travelstride.Model.Tour;

import java.util.List;

public class TourCreateRequest {
    private Tour tour;
    private Logistics logistics;
    private List<Long> activityIds;
    private List<Long> destinationIds;
    private List<Long> interestIds;
    private List<Long> styleIds;
    private List<Long> themeIds;
    private List<String> themes;

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public List<Long> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<Long> activityIds) {
        this.activityIds = activityIds;
    }

    public List<Long> getDestinationIds() {
        return destinationIds;
    }

    public void setDestinationIds(List<Long> destinationIds) {
        this.destinationIds = destinationIds;
    }

    public List<Long> getInterestIds() {
        return interestIds;
    }

    public void setInterestIds(List<Long> interestIds) {
        this.interestIds = interestIds;
    }

    public List<Long> getStyleIds() {
        return styleIds;
    }

    public void setStyleIds(List<Long> styleIds) {
        this.styleIds = styleIds;
    }

    public List<Long> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(List<Long> themeIds) {
        this.themeIds = themeIds;
    }

    public List<String> getThemes() {
        return themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
    }

    public TourCreateRequest(Tour tour, Logistics logistics, List<Long> activityIds, List<Long> destinationIds, List<Long> interestIds, List<Long> styleIds, List<Long> themeIds, List<String> themes) {
        this.tour = tour;
        this.logistics = logistics;
        this.activityIds = activityIds;
        this.destinationIds = destinationIds;
        this.interestIds = interestIds;
        this.styleIds = styleIds;
        this.themeIds = themeIds;
        this.themes = themes;
    }
}
