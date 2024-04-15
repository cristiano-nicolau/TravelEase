package hw.tqs.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "marked_trips")
public class MarkedTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "tripid")
    private Integer tripID;
    private Integer numPassagersChildren;
    private Integer numPassagersAdults;
    private List<String> seats;

    private String name;
    private String email;
    private String phone;
    private String nif;
    private String address;
    private String city;
    private String zipCode;
    private String cardType;
    private String cardNumber;


    public MarkedTrip(Integer tripID, Integer numPassagersChildren, Integer numPassagersAdults, List<String> seats, String name, String email, String phone, String nif, String address, String city, String zipCode, String cardType, String cardNumber) {
        this.tripID = tripID;
        this.numPassagersChildren = numPassagersChildren;
        this.numPassagersAdults = numPassagersAdults;
        this.seats = seats;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nif = nif;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
    }

    public MarkedTrip() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTripID() {
        return tripID;
    }

    public void setTripID(Integer tripID) {
        this.tripID = tripID;
    }

    public Integer getNumPassagersChildren() {
        return numPassagersChildren;
    }

    public void setNumPassagersChildren(Integer numPassagersChildren) {
        this.numPassagersChildren = numPassagersChildren;
    }

    public Integer getNumPassagersAdults() {
        return numPassagersAdults;
    }

    public void setNumPassagersAdults(Integer numPassagersAdults) {
        this.numPassagersAdults = numPassagersAdults;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public void addSeat(String seat) {
        if (this.seats == null) {
            this.seats = new ArrayList<>();
        }
        this.seats.add(seat);
    }

    public void removeSeat(String seat) {
        this.seats.remove(seat);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
