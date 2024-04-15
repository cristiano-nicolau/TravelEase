package hw.tqs.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import hw.tqs.controller.TripController;
import hw.tqs.model.MarkedTrip;
import hw.tqs.model.Trip;
import hw.tqs.repository.MarkedTripRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MarkedTripService {
    
        private static final Logger logger = LogManager.getLogger(TripController.class);


    @Autowired
    private MarkedTripRepository markedTripRepository;

    @Autowired
    private TripService tripService;

    public MarkedTrip saveMarkedTrip(MarkedTrip markedTrip) throws Exception {
        Trip trip = tripService.getTripById(markedTrip.getTripID());
        if (trip == null) {
            throw new Exception("Trip with ID " + markedTrip.getTripID() + " does not exist");
        }

        Integer availableSeats = trip.getSeats() - trip.getOccupiedSeats().size();
        List<String> occupiedSeats = trip.getOccupiedSeats();

        if (availableSeats <= 0) {
            throw new Exception("Trip with ID " + markedTrip.getTripID() + " is full");
        }

        for (String seat : markedTrip.getSeats()) {
            if (occupiedSeats.contains(seat)) {
                throw new Exception("Seat " + seat + " is already occupied");
            }
        }

        trip.setOccupiedSeats(markedTrip.getSeats());
        tripService.saveTrip(trip);

        return markedTripRepository.save(markedTrip);
    }

    @Transactional
    public void deleteMarkedTrip(Long id) {
        MarkedTrip markedTrip = markedTripRepository.findById(id);
    
        if (markedTrip != null) {
            Trip trip = tripService.getTripById(markedTrip.getTripID());
    
            if (trip != null) {
                 List<String> occupiedSeats = new ArrayList<>(trip.getOccupiedSeats()); // Criar uma nova lista
                for (String seat : markedTrip.getSeats()) {
                    occupiedSeats.remove(seat);
                }
                trip.setOccupiedSeats(occupiedSeats);
        
                markedTripRepository.deleteById(id);
            } else {
                logger.error("Trip not found for MarkedTrip with ID: " + id);
                throw new EntityNotFoundException("Trip not found for MarkedTrip with ID: " + id);
            }
        } else {
            logger.error("MarkedTrip not found with ID: " + id);
            throw new EntityNotFoundException("MarkedTrip not found with ID: " + id);
        }
    }
    
    public MarkedTrip updateMarkedTrip(Long id, MarkedTrip markedTrip) {
        markedTripRepository.deleteById(id);
        return markedTripRepository.save(markedTrip);
    }

    public boolean existsMarkedTrip(Long id) {
        return markedTripRepository.existsById(id);
    }

    public List<MarkedTrip> getMarkedTrips() {
        return markedTripRepository.findAll();
    }

    public MarkedTrip getMarkedTripById(Long id) {
        return markedTripRepository.findById(id);
    }

    public List<MarkedTrip> getMarkedTripsByTripID(Integer tripID) {
        return markedTripRepository.findByTripID(tripID);
    }

    
}
