package hw.tqs.serviceTest;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hw.tqs.model.MarkedTrip;
import hw.tqs.model.Trip;
import hw.tqs.repository.MarkedTripRepository;
import hw.tqs.services.MarkedTripService;
import hw.tqs.services.TripService;
import jakarta.persistence.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
public class MarkedTripServiceTest {

    @Mock
    private MarkedTripRepository markedTripRepository;

    @Mock
    private TripService tripService;

    @InjectMocks
    private MarkedTripService markedTripService;

    private MarkedTrip markedTrip1;

    private Trip trip1;

    @BeforeEach
    void setUp() {
        trip1 = new Trip("Porto", "Lisboa", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50, List.of("A12", "A20", "A30", "A40", "A25"));
        markedTrip1 = new MarkedTrip(trip1.getId(), 1,2, List.of("A15", "A22", "A31"), "John Doe", "johndoe@example.com", "123456789", "123456789", "123 Main St", "City", "12345", "Visa", "1234567890123456");
    }

    @Test
    @DisplayName("Test get marked trips by trip ID")
    void testGetMarkedTripByTripID() {
        when(markedTripRepository.findByTripID(trip1.getId())).thenReturn(List.of(markedTrip1));
        List<MarkedTrip> markedTrips = markedTripService.getMarkedTripsByTripID(trip1.getId());
        assertNotNull(markedTrips);
        assertFalse(markedTrips.isEmpty());
        assertEquals(1, markedTrips.size());
        assertEquals(markedTrip1, markedTrips.get(0));
    }

    @Test
    @DisplayName("Test get marked trip by trip ID with no marked trips")
    void testGetMarkedTripByTripIDWithNoMarkedTrips() {
        when(markedTripRepository.findByTripID(trip1.getId())).thenReturn(new ArrayList<>());
        List<MarkedTrip> markedTrips = markedTripService.getMarkedTripsByTripID(trip1.getId());
        assertNotNull(markedTrips);
        assertTrue(markedTrips.isEmpty());
    }

    @Test
    @DisplayName("Test get marked trip by ID")
    void testGetMarkedTripByID() {
        when(markedTripRepository.findById(markedTrip1.getId())).thenReturn(markedTrip1);
        MarkedTrip markedTrip = markedTripService.getMarkedTripById(markedTrip1.getId());
        assertNotNull(markedTrip);
        assertEquals(markedTrip1, markedTrip);
    }

    @Test
    @DisplayName("Test get marked trip by ID with no marked trip")
    void testGetMarkedTripByIDWithNoMarkedTrip() {
        when(markedTripRepository.findById(markedTrip1.getId())).thenReturn(null);
        MarkedTrip markedTrip = markedTripService.getMarkedTripById(markedTrip1.getId());
        assertNull(markedTrip);
    }

    @Test
    @DisplayName("Test get all marked trips")
    void testGetMarkedTrips() {
        List<MarkedTrip> markedTrips = List.of(markedTrip1);
        when(markedTripRepository.findAll()).thenReturn(markedTrips);
        assertEquals(markedTrips, markedTripService.getMarkedTrips());
    }

    @Test
    @DisplayName("Test exist marked trip by ID")
    void testExistsMarkedTripByID() {
        when(markedTripRepository.existsById(markedTrip1.getId())).thenReturn(true);
        assertTrue(markedTripService.existsMarkedTrip(markedTrip1.getId()));
    }

    @Test
    @DisplayName("Test does not exist marked trip by ID")
    void testDoesNotExistMarkedTripByID() {
        when(markedTripRepository.existsById(markedTrip1.getId())).thenReturn(false);
        assertFalse(markedTripService.existsMarkedTrip(markedTrip1.getId()));
    }

    @Test
    @DisplayName("Test save marked trip with available seats")
    void testSaveMarkedTripWithAvailableSeats() throws Exception {
        when(markedTripRepository.save(markedTrip1)).thenReturn(markedTrip1);
        when(tripService.getTripById(markedTrip1.getTripID())).thenReturn(trip1);
        MarkedTrip savedMarkedTrip = markedTripService.saveMarkedTrip(markedTrip1);
        assertNotNull(savedMarkedTrip);
        assertEquals(markedTrip1, savedMarkedTrip);
    }

    @Test
    @DisplayName("Test save marked trip with  seats already occupied")
    void testSaveMarkedTripWithSeatsAlreadyOccupied() {
        when(tripService.getTripById(markedTrip1.getTripID())).thenReturn(trip1);
        trip1.setOccupiedSeats(List.of("A12", "A22", "A31", "A15", "A25"));
        assertThrows(Exception.class, () -> markedTripService.saveMarkedTrip(markedTrip1));
    }

    @Test
    @DisplayName("Test save marked trip with trip not found")
    void testSaveMarkedTripWithTripNotFound() {
        when(tripService.getTripById(markedTrip1.getTripID())).thenReturn(null);
        assertThrows(Exception.class, () -> markedTripService.saveMarkedTrip(markedTrip1));
    }

    @Test
    @DisplayName("Test save marked trip with no seats available")
    void testSaveMarkedTripWithNoSeatsAvailable() {
        when(tripService.getTripById(markedTrip1.getTripID())).thenReturn(trip1);
        trip1.setSeats(1);
        trip1.setOccupiedSeats(List.of("A01"));
        assertThrows(Exception.class, () -> markedTripService.saveMarkedTrip(markedTrip1));
    }


    @Test
    @DisplayName("Test delete marked trip")
    void testDeleteMarkedTrip() {
        when(markedTripRepository.findById(markedTrip1.getId())).thenReturn(markedTrip1);
        when(tripService.getTripById(markedTrip1.getTripID())).thenReturn(trip1);
        List<String> occupiedSeats = markedTrip1.getSeats();
        trip1.setOccupiedSeats(occupiedSeats);
        markedTripService.deleteMarkedTrip(markedTrip1.getId());
        verify(markedTripRepository, times(1)).findById(markedTrip1.getId());
        verify(tripService, times(1)).getTripById(markedTrip1.getTripID());
        verify(markedTripRepository, times(1)).deleteById(markedTrip1.getId());
        assertEquals(0, trip1.getOccupiedSeats().size());
    }

    @Test
    @DisplayName("Test delete marked trip that does not exist")
    void testDeleteMarkedTripNotFound() {
        when(markedTripRepository.findById(markedTrip1.getId())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> markedTripService.deleteMarkedTrip(markedTrip1.getId()));
    }

    @Test
    @DisplayName("Test delete marked trip with trip that does not exist")
    void testDeleteMarkedTripWithTripNotFound() {
        when(markedTripRepository.findById(markedTrip1.getId())).thenReturn(markedTrip1);
        when(tripService.getTripById(markedTrip1
        .getTripID())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> markedTripService.deleteMarkedTrip(markedTrip1.getId()));
    }

    @Test
    @DisplayName("Test update marked trip")
    void testUpdateMarkedTrip() {
        when(markedTripRepository.save(markedTrip1)).thenReturn(markedTrip1);
        MarkedTrip updatedMarkedTrip = markedTripService.updateMarkedTrip(markedTrip1.getId(), markedTrip1);
        assertNotNull(updatedMarkedTrip);
        assertEquals(markedTrip1, updatedMarkedTrip);
    }
    

}
