package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_request")
@Data
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String homeCountry;
    private String mainCountry;
    private String additionalCountries;
    private String companionsAges;

    @Enumerated(EnumType.STRING)
    private DateType dateType;

    private LocalDate startDate;
    private LocalDate endDate;
    private String tripType;
    private String lodgingType;
    private BigDecimal budgetPerPerson;

    @Enumerated(EnumType.STRING)
    private BudgetStrictness budgetStrictness;

    @Lob
    private String activityDetail;
    private String firstName;
    private String whenDate;
    private String totalDate;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String contactMethod;
    @Lob
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum DateType {
        exact, approximate, decide_later
    }

    public String getWhenDate() {
        return whenDate;
    }

    public void setWhenDate(String whenDate) {
        this.whenDate = whenDate;
    }

    public String getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(String totalDate) {
        this.totalDate = totalDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum BudgetStrictness {
        strict, flexible
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public String getMainCountry() {
        return mainCountry;
    }

    public void setMainCountry(String mainCountry) {
        this.mainCountry = mainCountry;
    }

    public String getAdditionalCountries() {
        return additionalCountries;
    }

    public void setAdditionalCountries(String additionalCountries) {
        this.additionalCountries = additionalCountries;
    }

    public String getCompanionsAges() {
        return companionsAges;
    }

    public void setCompanionsAges(String companionsAges) {
        this.companionsAges = companionsAges;
    }

    public DateType getDateType() {
        return dateType;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getLodgingType() {
        return lodgingType;
    }

    public void setLodgingType(String lodgingType) {
        this.lodgingType = lodgingType;
    }

    public BigDecimal getBudgetPerPerson() {
        return budgetPerPerson;
    }

    public void setBudgetPerPerson(BigDecimal budgetPerPerson) {
        this.budgetPerPerson = budgetPerPerson;
    }

    public BudgetStrictness getBudgetStrictness() {
        return budgetStrictness;
    }

    public void setBudgetStrictness(BudgetStrictness budgetStrictness) {
        this.budgetStrictness = budgetStrictness;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
