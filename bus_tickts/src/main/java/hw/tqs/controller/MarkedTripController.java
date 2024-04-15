package hw.tqs.controller;

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

import hw.tqs.services.MarkedTripService;
import hw.tqs.model.MarkedTrip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api/mark/trip")
public class MarkedTripController {

    private static final Logger logger = LogManager.getLogger(TripController.class);

    @Autowired
    private MarkedTripService markedTripService;

    @GetMapping("/")
    public ResponseEntity<?> getMarkedTrips() {
        List<MarkedTrip> markedTrips = markedTripService.getMarkedTrips();
        if (markedTrips.isEmpty()) {
            logger.info("No marked trips found");
            return new ResponseEntity<>("No marked trips found", HttpStatus.NOT_FOUND);
        }
        logger.info("Retrieved marked trips successfully");
        return new ResponseEntity<>(markedTrips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMarkedTripById(@PathVariable String id) {
        try {
            Long tripId = Long.parseLong(id);
            MarkedTrip markedTrip = markedTripService.getMarkedTripById(tripId);
            if (markedTrip == null) {
                logger.info("Marked trip not found");
                return new ResponseEntity<>("Marked trip not found", HttpStatus.NOT_FOUND);
            }
            logger.info("Retrieved marked trip successfully");
            return new ResponseEntity<>(markedTrip, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Internal Server Error", e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trip/{tripID}")
    public ResponseEntity<?> getMarkedTripsByTripID(@PathVariable Integer tripID) {
        List<MarkedTrip> markedTrips = markedTripService.getMarkedTripsByTripID(tripID);
        if (markedTrips.isEmpty()) {
            logger.info("No marked trips found");
            return new ResponseEntity<>("No marked trips found", HttpStatus.NOT_FOUND);
        }
        logger.info("Retrieved marked trips successfully");
        return new ResponseEntity<>(markedTrips, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveMarkedTrip(@RequestBody MarkedTrip markedTrip) {
        try {
            MarkedTrip savedMarkedTrip = markedTripService.saveMarkedTrip(markedTrip);
            logger.info("Saved marked trip successfully");
            Long id = savedMarkedTrip.getId();
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Bad Request", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarkedTrip(@PathVariable("id") String id) {
        try {
            Long tripId = Long.parseLong(id);
            if (!markedTripService.existsMarkedTrip(tripId)) {
                logger.info("Marked trip not found");
                return new ResponseEntity<>("Marked trip not found", HttpStatus.NOT_FOUND);
            }
            markedTripService.deleteMarkedTrip(tripId);
            logger.info("Marked trip with ID " + tripId + " deleted successfully.");
            return new ResponseEntity<>("Marked trip with ID " + id + " deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to delete marked trip with ID: " + id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    
}
