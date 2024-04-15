package hw.tqs.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import hw.tqs.model.Trip;
import hw.tqs.repository.TripRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Trip trip1;
    private Trip trip2;

    @BeforeEach
    void setUp() {
        trip1 = new Trip("Porto", "Lisboa", LocalDate.now().plusDays(50), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));
        trip2 = new Trip("Lisboa", "Faro", LocalDate.now().plusDays(50), LocalTime.now(), LocalTime.now().plusHours(3), 60.0, 50,
                List.of("A10", "A15", "A20", "A25"));
    }

    @AfterEach
    void tearDown() {
        tripRepository.delete(trip1);
        tripRepository.delete(trip2);
    }

    @Test
    @DisplayName("Test save trip")
    void testSaveTrip() {
        Trip savedTrip = tripRepository.save(trip1);
        assertThat(savedTrip).isEqualTo(trip1);
    }



    @Test
    @DisplayName("Test find trip by id")
    void testFindTripById() {
        entityManager.persist(trip1);
        entityManager.persist(trip2);
        Trip foundTrip = tripRepository.findById(trip1.getId()).orElse(null);
        assertThat(foundTrip).isEqualTo(trip1);
    }

    @Test
    @DisplayName("Test find trip by origin and destination")
    void testFindTripByOriginAndDestination() {
        entityManager.persist(trip1);
        entityManager.persist(trip2);
        List<Trip> foundTrips = tripRepository.findByOriginAndDestination(trip1.getOrigin(), trip1.getDestination());
        assertThat(foundTrips).contains(trip1);
        List<Trip> foundTrips2 = tripRepository.findByOriginAndDestination(trip2.getOrigin(), trip2.getDestination());
        assertThat(foundTrips2).contains(trip2);
    }

    @Test
    @DisplayName("Test find trip by origin, destination and departure date")
    void testFindTripByOriginAndDestinationAndDepartureDate() {
        entityManager.persist(trip1);
        entityManager.persist(trip2);
        List<Trip> foundTrips = tripRepository.findByOriginAndDestinationAndDepartureDate(trip1.getOrigin(),
                trip1.getDestination(), trip1.getDepartureDate());
        assertThat(foundTrips).hasSize(1).contains(trip1);
        List<Trip> foundTrips2 = tripRepository.findByOriginAndDestinationAndDepartureDate(trip2.getOrigin(),
                trip2.getDestination(), trip2.getDepartureDate());
        assertThat(foundTrips2).hasSize(1).contains(trip2);
    }

    @Test
    @DisplayName("Test delete trip")
    void testDeleteTrip() {
        Integer alldata = tripRepository.findAll().size();
        entityManager.persist(trip1);
        entityManager.persist(trip2);
        entityManager.flush();
        tripRepository.deleteById(trip1.getId());
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(alldata + 1).contains(trip2);
        tripRepository.deleteById(trip2.getId());
        List<Trip> trips2 = tripRepository.findAll();
        assertThat(trips2).hasSize(alldata);
    }

    @Test
    @DisplayName("Test get all trip")
    void testGetAllTrips() {
        entityManager.persist(trip1);
        entityManager.persist(trip2);
        List<Trip> trips = tripRepository.findAll();
        assertThat(trips).hasSize(trips.size()).contains(trip1, trip2);
    }


}
