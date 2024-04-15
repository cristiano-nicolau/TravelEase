package hw.tqs.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hw.tqs.model.Trip;
import hw.tqs.services.TripService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private static final Logger logger = LogManager.getLogger(TripController.class);

    @Autowired
    private TripService tripService;

    @GetMapping("/")
    public ResponseEntity<?> getTrips() {
        List<Trip> trips = tripService.getTrips();
        if (trips.isEmpty()) {
            logger.info("No trips found");
            return new ResponseEntity<>("No trips found", HttpStatus.NOT_FOUND);
        }
        logger.info("Retrieved trips successfully");
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTripById(@PathVariable Integer id) {
        Trip trip = tripService.getTripById(id);
        if (trip == null) {
            logger.info("Trip not found");
            return new ResponseEntity<>("Trip not found", HttpStatus.NOT_FOUND);
        }
        logger.info("Retrieved trip successfully");
        return new ResponseEntity<>(trip, HttpStatus.OK);
    }

    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<?> getTripsByOriginAndDestination(@PathVariable String origin, @PathVariable String destination) {
        List<Trip> trips = tripService.getTripsByOriginAndDestination(origin, destination);
        if (trips.isEmpty()) {
            logger.info("No trips found");
            return new ResponseEntity<>("No trips found", HttpStatus.NOT_FOUND);
        }
        logger.info("Retrieved trips successfully");
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @GetMapping("/{origin}/{destination}/{departureDate}")
    public ResponseEntity<?> getTripByOriginAndDestinationAndDepartureDate(@PathVariable String origin, @PathVariable String destination, @PathVariable String departureDate) {
        try{
            LocalDate date = LocalDate.parse(departureDate);
            if (date.isBefore(LocalDate.now())) {
                return new ResponseEntity<>("Invalid date", HttpStatus.BAD_REQUEST);
            }
            List<Trip> trips = tripService.getTripsByOriginAndDestinationAndDepartureDate(origin, destination, date);
            if (trips.isEmpty()) {
                return new ResponseEntity<>("No trips found", HttpStatus.NOT_FOUND);
            }
            logger.info("Retrieved trips successfully");
            return new ResponseEntity<>(trips, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal Server Error", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/")
    public ResponseEntity<?> addTrip(@RequestBody Trip trip) {
        if (trip.getDepartureDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>("Invalid date", HttpStatus.BAD_REQUEST);
        }
        Trip newTrip = tripService.saveTrip(trip);
        logger.info("Trip added successfully");
        return new ResponseEntity<>(newTrip, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Integer id) {
        if (tripService.getTripById(id) == null) {
               logger.info("Trip not found");
            return new ResponseEntity<>("Trip not found", HttpStatus.NOT_FOUND);
        }
        tripService.deleteTrip(id);
        logger.info("Trip deleted successfully");
        return new ResponseEntity<>("Trip deleted successfully", HttpStatus.OK);

    }
}
