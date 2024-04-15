package hw.tqs.serviceTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hw.tqs.model.Trip;
import hw.tqs.repository.TripRepository;
import hw.tqs.services.TripService;
import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    private Trip trip1;
    private Trip trip2;
    private Trip trip3;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    @BeforeEach
    void setUp(){
        trip1 = new Trip("Porto", "Lisboa", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50,List.of("A12", "A20", "A30", "A40", "A25"));
        trip2 = new Trip("Lisboa", "Faro", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(3), 60.0, 50,List.of("A10", "A15", "A20", "A25"));
        trip3 = new Trip("Porto", "Faro", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(4), 70.0, 50);
    }

    @Test
    @DisplayName("Test get all trips")
    void testGetTrips() {
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        when(tripRepository.findAll()).thenReturn(trips);
        assertThat(tripService.getTrips()).isEqualTo(trips);
    }

    @Test
    @DisplayName("Test get trip by id")
    void testGetTripById() {
        when(tripRepository.findById(1)).thenReturn(Optional.of(trip1));
        assertThat(tripService.getTripById(1)).isEqualTo(trip1);
    }

    @Test
    @DisplayName("Test get trip by id not found")
    void testGetTripByIdNotFound() {
        when(tripRepository.findById(1)).thenReturn(Optional.empty());
        assertThat(tripService.getTripById(1)).isNull();
    }

    @Test
    @DisplayName("Test get trips by origin and destination")
    void testGetTripsByOriginAndDestination() {
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        when(tripRepository.findByOriginAndDestination(anyString(), anyString())).thenReturn(trips);
        assertThat(tripService.getTripsByOriginAndDestination("Porto", "Lisboa")).isEqualTo(trips);
    }

    @Test
    @DisplayName("Test get trips by origin and destination not found")
    void testGetTripsByOriginAndDestinationNotFound() {
        List<Trip> trips = new ArrayList<>();
        when(tripRepository.findByOriginAndDestination(anyString(), anyString())).thenReturn(trips);
        assertThat(tripService.getTripsByOriginAndDestination("Porto", "Lisboa")).isEmpty();
    }

    @Test
    @DisplayName("Test get trips by origin, destination and departure date")
    void testGetTripsByOriginAndDestinationAndDepartureDate() {
        List<Trip> trips = List.of(trip1);
        when(tripRepository.findByOriginAndDestinationAndDepartureDate(anyString(), anyString(), any(LocalDate.class))).thenReturn(trips);
        assertThat(tripService.getTripsByOriginAndDestinationAndDepartureDate("Porto", "Lisboa", LocalDate.now())).isEqualTo(trips);
    }

    @Test
    @DisplayName("Test get trips by origin, destination and departure date not found")
    void testGetTripsByOriginAndDestinationAndDepartureDateNotFound() {
        List<Trip> trips = new ArrayList<>();
        when(tripRepository.findByOriginAndDestinationAndDepartureDate(anyString(), anyString(), any(LocalDate.class))).thenReturn(trips);
        assertThat(tripService.getTripsByOriginAndDestinationAndDepartureDate("Porto", "Lisboa", LocalDate.now())).isEmpty();
    }

    @Test
    @DisplayName("Test save trip")
    void testSaveTrip() {
        when(tripRepository.save(trip1)).thenReturn(trip1);
        assertThat(tripService.saveTrip(trip1)).isEqualTo(trip1);
    }

    @Test
    @DisplayName("Test save trips")
    void testSaveTrips() {
        List<Trip> trips = List.of(trip1, trip2, trip3);
        when(tripRepository.saveAll(trips)).thenReturn(trips);
        assertThat(tripService.saveTrips(trips)).isEqualTo(trips);
    }

    @Test
    @DisplayName("Test delete trip")
    void testDeleteTrip() {
        when(tripRepository.existsById(1)).thenReturn(true);
        tripService.deleteTrip(1);
        verify(tripRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Test delete trip that does not exist")
    void testDeleteTripNotFound() {
        when(tripRepository.existsById(1)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> tripService.deleteTrip(1));
    }

    @Test
    @DisplayName("Test delete all trips")
    void testDeleteAllTrips() {
        tripService.deleteAllTrips();
        verify(tripRepository, times(1)).deleteAll();
    }

    @Test
    @DisplayName("Test update trip")
    void testUpdateTrip() {
        when(tripRepository.existsById(1)).thenReturn(true);
        when(tripRepository.save(trip2)).thenReturn(trip2);
        assertThat(tripService.updateTrip(1, trip2)).isEqualTo(trip2);
    }

    @Test
    @DisplayName("Test update trip that does not exist")
    void testUpdateTripNotFound() {
        when(tripRepository.existsById(1)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> tripService.updateTrip(1, trip2));
    }
}
