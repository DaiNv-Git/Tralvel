package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tour_reservation")
@Data
public class TourReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(length = 100)
    private String nationality;

    @Column(name = "country_of_residence", length = 100)
    private String countryOfResidence;

    @Column(length = 150)
    private String email;
    
    
    @Column(name = "companions_ages", length = 255)
    private String companionsAges; 

    @Column(name = "primary_contact", length = 150)
    private String primaryContact;

    @Column(name = "mobile_phone", length = 20)
    private String mobilePhone;

    // tourId kiểu Long
    @Column(name = "tour_id")
    private Long tourId;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "number_of_travelers")
    private Integer numberOfTravelers;

    @Column(name = "single_room")
    private Integer singleRoom;

    @Column(name = "shared_room")
    private Integer sharedRoom;
    @Column(name = "people_per_room")
    private Integer peoplePerRoom;

    // Lưu dạng "EMAIL,SMS,ZALO"
    @Column(name = "additional_contact_preferences", length = 255)
    private String additionalContactPreferences;
    
    private String totalPrices;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", insertable = false, updatable = false)
    private Tour tour;

    public TourReservation() {
    }

    public TourReservation(Long id, String firstName, String lastName, String nationality, String countryOfResidence, String email, String primaryContact, String mobilePhone, Long tourId, LocalDate departureDate, Integer numberOfTravelers, Integer singleRoom, Integer sharedRoom, String additionalContactPreferences, LocalDateTime createdAt, Tour tour) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.countryOfResidence = countryOfResidence;
        this.email = email;
        this.primaryContact = primaryContact;
        this.mobilePhone = mobilePhone;
        this.tourId = tourId;
        this.departureDate = departureDate;
        this.numberOfTravelers = numberOfTravelers;
        this.singleRoom = singleRoom;
        this.sharedRoom = sharedRoom;
        this.additionalContactPreferences = additionalContactPreferences;
        this.createdAt = createdAt;
        this.tour = tour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public Integer getSingleRoom() {
        return singleRoom;
    }

    public void setSingleRoom(Integer singleRoom) {
        this.singleRoom = singleRoom;
    }

    public Integer getSharedRoom() {
        return sharedRoom;
    }

    public void setSharedRoom(Integer sharedRoom) {
        this.sharedRoom = sharedRoom;
    }

    public String getAdditionalContactPreferences() {
        return additionalContactPreferences;
    }

    public void setAdditionalContactPreferences(String additionalContactPreferences) {
        this.additionalContactPreferences = additionalContactPreferences;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }
}
