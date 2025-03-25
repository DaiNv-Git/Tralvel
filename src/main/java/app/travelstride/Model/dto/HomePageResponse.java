package app.travelstride.Model.dto;

import app.travelstride.Model.BannerGroup;
import app.travelstride.Model.Destination;
import app.travelstride.Model.Post;
import app.travelstride.Model.Styles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePageResponse {
    private List<BannerGroup> banners;
    private List<Styles> styles;
    private List<DestinationResponse> destinations;
    private List<Post> pots;
    private Map<String, Integer> destinationCount;
    Map<String, Object> findTrips;

    public Map<String, Object> getFindTrips() {
        return findTrips;
    }

    public void setFindTrips(Map<String, Object> findTrips) {
        this.findTrips = findTrips;
    }

    public List<Post> getPots() {
        return pots;
    }

    public void setPots(List<Post> pots) {
        this.pots = pots;
    }

    public List<BannerGroup> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerGroup> banners) {
        this.banners = banners;
    }

    public List<Styles> getStyles() {
        return styles;
    }

    public void setStyles(List<Styles> styles) {
        this.styles = styles;
    }

    public List<DestinationResponse> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<DestinationResponse> destinations) {
        this.destinations = destinations;
    }

    public Map<String, Integer> getDestinationCount() {
        return destinationCount;
    }

    public void setDestinationCount(Map<String, Integer> destinationCount) {
        this.destinationCount = destinationCount;
    }
}
