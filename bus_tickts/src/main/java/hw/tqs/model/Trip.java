package hw.tqs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Double price;
    private Integer seats;
    private List<String> occupiedSeats;

    public Trip(String origin, String destination, LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime, double price, int seats) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.seats = seats;
        this.occupiedSeats = new ArrayList<>();
    }

    public Trip(String origin, String destination, LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime, double price, int seats, List<String> occupiedSeats) {
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.seats = seats;
        this.occupiedSeats = occupiedSeats;
    }
    public Trip() {
    }

    

    public void addOccupiedSeat(String seat) {
        this.occupiedSeats.add(seat);
    }

    public boolean isSeatOccupied(String seat) {
        return this.occupiedSeats.contains(seat);
    }

    public boolean hasAvailableSeats() {
        return this.occupiedSeats.size() < this.seats;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<String> getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(List<String> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
