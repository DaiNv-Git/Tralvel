package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "logistics")
@Getter
@Setter

public class Logistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tourId;

    @Lob
    private String logistics;

    @Lob
    private String accommodation;

    @Lob
    private String transportation;

    @Lob
    private String guides;

    @Lob
    private String travelInsurance;

    @Lob
    private String visaRequirements;

    @Lob
    private String healthSafety;

    private Integer mealsIncludedBreakfast;
    private Integer mealsIncludedLunch;

    private Integer ageMin;
    private Integer ageMax;

    @Lob
    private String additionalInfo;

    @Lob
    private String cancellationPolicy;

    public Logistics() {
    }

    public Logistics(Long id, Long tourId, String logistics, String accommodation, String transportation, String guides, String travelInsurance, String visaRequirements, String healthSafety, Integer mealsIncludedBreakfast, Integer mealsIncludedLunch, Integer ageMin, Integer ageMax, String additionalInfo, String cancellationPolicy) {
        this.id = id;
        this.tourId = tourId;
        this.logistics = logistics;
        this.accommodation = accommodation;
        this.transportation = transportation;
        this.guides = guides;
        this.travelInsurance = travelInsurance;
        this.visaRequirements = visaRequirements;
        this.healthSafety = healthSafety;
        this.mealsIncludedBreakfast = mealsIncludedBreakfast;
        this.mealsIncludedLunch = mealsIncludedLunch;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.additionalInfo = additionalInfo;
        this.cancellationPolicy = cancellationPolicy;
    }

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

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getGuides() {
        return guides;
    }

    public void setGuides(String guides) {
        this.guides = guides;
    }

    public String getTravelInsurance() {
        return travelInsurance;
    }

    public void setTravelInsurance(String travelInsurance) {
        this.travelInsurance = travelInsurance;
    }

    public String getVisaRequirements() {
        return visaRequirements;
    }

    public void setVisaRequirements(String visaRequirements) {
        this.visaRequirements = visaRequirements;
    }

    public String getHealthSafety() {
        return healthSafety;
    }

    public void setHealthSafety(String healthSafety) {
        this.healthSafety = healthSafety;
    }

    public Integer getMealsIncludedBreakfast() {
        return mealsIncludedBreakfast;
    }

    public void setMealsIncludedBreakfast(Integer mealsIncludedBreakfast) {
        this.mealsIncludedBreakfast = mealsIncludedBreakfast;
    }

    public Integer getMealsIncludedLunch() {
        return mealsIncludedLunch;
    }

    public void setMealsIncludedLunch(Integer mealsIncludedLunch) {
        this.mealsIncludedLunch = mealsIncludedLunch;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }
}
