package hw.tqs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hw.tqs.model.Trip;

import java.time.LocalDate;
import java.util.List;


public interface TripRepository extends JpaRepository<Trip, Integer>{
    public List<Trip> findByOriginAndDestination(String origin, String destination);
    public List<Trip> findByOriginAndDestinationAndDepartureDate(String origin, String destination, LocalDate departureDate);
    boolean existsById(Integer id);
}
