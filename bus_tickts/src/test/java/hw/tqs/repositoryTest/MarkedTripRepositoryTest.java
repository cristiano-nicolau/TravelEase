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

import hw.tqs.model.*;
import hw.tqs.repository.TripRepository;
import hw.tqs.repository.MarkedTripRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MarkedTripRepositoryTest {
    
    @Autowired  
    private TripRepository tripRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired 
    private MarkedTripRepository markedTripRepository;

    private Trip trip1;
    private MarkedTrip mkt1;
    private MarkedTrip mkt2;

    @BeforeEach
    void setUp() {
        trip1 = new Trip("Porto", "Lisboa", LocalDate.now().plusDays(50), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));
        tripRepository.save(trip1);

        mkt1 = new MarkedTrip(trip1.getId(), 1, 2, List.of("A15", "A22", "A31"), "John Doe", "johndoe@example.com", "123456789", "123456789", "123 Main St", "City", "12345", "Visa", "1234567890123456");
        mkt2 = new MarkedTrip(trip1.getId(), 2, 1, List.of("A13", "A21", "A35"), "Jona Doe", "jona@email.com", "987654321", "987654321", "987 Main St", "City", "54321", "Mastercard", "9876543210987654");
        markedTripRepository.save(mkt1);
        markedTripRepository.save(mkt2);
    }

    @AfterEach
    void tearDown() {
        markedTripRepository.delete(mkt1);
        markedTripRepository.delete(mkt2);
        tripRepository.delete(trip1);
    }

    @Test
    @DisplayName("Test save marked trip")
    void testSaveMarkedTrip() {
        MarkedTrip savedMarkedTrip = markedTripRepository.save(mkt1);
        assertThat(savedMarkedTrip).isEqualTo(mkt1);
    }

    @Test
    @DisplayName("Test find marked trip by id")
    void testFindMarkedTripById() {
        MarkedTrip foundMarkedTrip = markedTripRepository.findById(mkt1.getId());
        assertThat(foundMarkedTrip).isEqualTo(mkt1);
    }

    @Test
    @DisplayName("Test find marked trip by id not found")
    void testFindMarkedTripByIdNotFound() {
        MarkedTrip foundMarkedTrip = markedTripRepository.findById(3L);
        assertThat(foundMarkedTrip).isNull();
    }

    @Test
    @DisplayName("Test find marked trip by trip ID")
    void testFindMarkedTripByTripID() {
        List<MarkedTrip> foundMarkedTrips = markedTripRepository.findByTripID(trip1.getId());
        assertThat(foundMarkedTrips).contains(mkt1);
        assertThat(foundMarkedTrips).contains(mkt2);
    }

    @Test
    @DisplayName("Test find marked trip by trip ID not found")
    void testFindMarkedTripByTripIDNotFound() {
        List<MarkedTrip> foundMarkedTrips = markedTripRepository.findByTripID(3);
        assertThat(foundMarkedTrips).isEmpty();
    }

    @Test
    @DisplayName("Test exists marked trip by id")
    void testExistsMarkedTripById() {
        boolean exists = markedTripRepository.existsById(mkt1.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Test exists marked trip by id not found")
    void testExistsMarkedTripByIdNotFound() {
        boolean exists = markedTripRepository.existsById(3L);
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Test delete marked trip by id")
    void testDeleteMarkedTripById() {
        markedTripRepository.deleteById(mkt1.getId());
        assertThat(markedTripRepository.existsById(mkt1.getId())).isFalse();
    }

    @Test
    @DisplayName("Test delete marked trip by id not found")
    void testDeleteMarkedTripByIdNotFound() {
        markedTripRepository.deleteById(3L);
        assertThat(markedTripRepository.existsById(3L)).isFalse();
    }

}
