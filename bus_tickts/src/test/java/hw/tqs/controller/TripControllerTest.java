package hw.tqs.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import hw.tqs.model.Trip;
import hw.tqs.services.TripService;


@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @Test
    @DisplayName("Test get all trips")
    void testGetTrips() throws Exception {

        LocalDate departureDate = LocalDate.of(2024, 4, 10);
        LocalTime departureTime = LocalTime.of(10, 0);
        LocalTime arrivalTime = LocalTime.of(12, 0);

        Trip trip = new Trip("Porto", "Lisboa", departureDate, departureTime, arrivalTime, 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));

        when(tripService.getTrips()).thenReturn(List.of(trip));
        mockMvc.perform(get("/api/trips/")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].origin").value("Porto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination").value("Lisboa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departureDate").value(departureDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(50.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seats").value(50));

        verify(tripService, times(1)).getTrips();
    }

    @Test
    @DisplayName("Test get all trips but none found")
    void testGetTripsButNoneFound() throws Exception {
        when(tripService.getTrips()).thenReturn(List.of());
        mockMvc.perform(get("/api/trips/")).andExpect(status().isNotFound());
        verify(tripService, times(1)).getTrips();
    }

    @Test
    @DisplayName("Test get trip by id")
    void testGetTripById() throws Exception {
        LocalDate departureDate = LocalDate.of(2024, 4, 10);
        LocalTime departureTime = LocalTime.of(10, 0);
        LocalTime arrivalTime = LocalTime.of(12, 0);

        Trip trip = new Trip("Porto", "Lisboa", departureDate, departureTime, arrivalTime, 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));

        when(tripService.getTripById(1)).thenReturn(trip);
        mockMvc.perform(get("/api/trips/1")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.origin").value("Porto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Lisboa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departureDate").value(departureDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(50.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seats").value(50));
        verify(tripService, times(1)).getTripById(1);
    }

    @Test
    @DisplayName("Test get trip by id not found")
    void testGetTripByIdNotFound() throws Exception {
        when(tripService.getTripById(1)).thenReturn(null);
        mockMvc.perform(get("/api/trips/1")).andExpect(status().isNotFound());
        verify(tripService, times(1)).getTripById(1);
    }

    @Test
    @DisplayName("Test get trip by id with invalid id")
    void testGetTripByIdWithInvalidId() throws NumberFormatException, Exception{
        mockMvc.perform(get("/api/trips/abc"))
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("Test get trips by origin and destination")
    void testGetTripsByOriginAndDestination() throws Exception {
        LocalDate departureDate = LocalDate.of(2024, 4, 10);
        LocalTime departureTime = LocalTime.of(10, 0);
        LocalTime arrivalTime = LocalTime.of(12, 0);

        Trip trip = new Trip("Porto", "Lisboa", departureDate, departureTime, arrivalTime, 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));

        when(tripService.getTripsByOriginAndDestination("Porto", "Lisboa")).thenReturn(List.of(trip));
        mockMvc.perform(get("/api/trips/Porto/Lisboa")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].origin").value("Porto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination").value("Lisboa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departureDate").value(departureDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(50.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seats").value(50));
        verify(tripService, times(1)).getTripsByOriginAndDestination("Porto", "Lisboa");
    }

    @Test
    @DisplayName("Test get trips by origin and destination not found")
    void testGetTripsByOriginAndDestinationNotFound() throws Exception {
        when(tripService.getTripsByOriginAndDestination("Porto", "Lisboa")).thenReturn(List.of());
        mockMvc.perform(get("/api/trips/Porto/Lisboa")).andExpect(status().isNotFound());
        verify(tripService, times(1)).getTripsByOriginAndDestination("Porto", "Lisboa");
    }

    @Test
    @DisplayName("Test get trips by origin, destination and departure date")
    void testGetTripsByOriginAndDestinationAndDepartureDate() throws Exception {
        LocalDate departureDate = LocalDate.of(2024, 4, 10);
        LocalTime departureTime = LocalTime.of(10, 0);
        LocalTime arrivalTime = LocalTime.of(12, 0);

        Trip trip = new Trip("Porto", "Lisboa", departureDate, departureTime, arrivalTime, 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));

        when(tripService.getTripsByOriginAndDestinationAndDepartureDate("Porto", "Lisboa", departureDate))
                .thenReturn(List.of(trip));
        mockMvc.perform(get("/api/trips/Porto/Lisboa/2024-04-10"))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].origin").value("Porto"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination").value("Lisboa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departureDate").value(departureDate.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(50.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seats").value(50));
        verify(tripService, times(1)).getTripsByOriginAndDestinationAndDepartureDate("Porto", "Lisboa", departureDate);
    }

    @Test
    @DisplayName("Test get trips by origin, destination and departure date not found")
    void testGetTripsByOriginAndDestinationAndDepartureDateNotFound() throws Exception {
        LocalDate departureDate = LocalDate.of(2024, 5, 10);
        when(tripService.getTripsByOriginAndDestinationAndDepartureDate("Porto", "Lisboa", LocalDate.now()))
                .thenReturn(List.of());
        mockMvc.perform(get("/api/trips/Porto/Lisboa/2024-05-10")).andExpect(status().isNotFound());
        verify(tripService, times(1)).getTripsByOriginAndDestinationAndDepartureDate("Porto", "Lisboa", departureDate);
    }

    @Test
    @DisplayName("Test get trips by origin, destination and departure date with invalid date")
    void testGetTripsByOriginAndDestinationAndDepartureDateWithInvalidDate() throws Exception {
        LocalDate departureDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        mockMvc.perform(get("/api/trips/Porto/Lisboa/" + departureDate)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test get trips by origin, destination and departure date with invalid date format")
    void testGetTripsByOriginAndDestinationAndDepartureDateWithInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/trips/Porto/Lisboa/2024-04-10T10:00:00")).andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Test add trip")
    void testAddTrip() throws Exception {
        LocalDate departureDate = LocalDate.of(2024, 4, 10);
        LocalTime departureTime = LocalTime.of(10, 0);
        LocalTime arrivalTime = LocalTime.of(12, 0);

        Trip trip = new Trip("Porto", "Lisboa", departureDate, departureTime, arrivalTime, 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));

        when(tripService.saveTrip(any(Trip.class))).thenReturn(trip);
        
        // Criando um ObjectMapper com o módulo JavaTimeModule registrado
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        // Realizando a requisição POST usando o ObjectMapper personalizado
        mockMvc.perform(post("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isCreated());
        verify(tripService, times(1)).saveTrip(any(Trip.class));
    }

    @Test
    @DisplayName("Test add trip with invalid date")
    void testAddTripWithInvalidDate() throws Exception {
        LocalDate departureDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        LocalTime departureTime = LocalTime.of(10, 0);
        LocalTime arrivalTime = LocalTime.of(12, 0);

        Trip trip = new Trip("Porto", "Lisboa", departureDate, departureTime, arrivalTime, 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));

        when(tripService.saveTrip(any(Trip.class))).thenReturn(trip);
        
        // Criando um ObjectMapper com o módulo JavaTimeModule registrado
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        // Realizando a requisição POST usando o ObjectMapper personalizado
        mockMvc.perform(post("/api/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trip)))
                .andExpect(status().isBadRequest());
        verify(tripService, times(0)).saveTrip(any(Trip.class));
    }


    @Test
    @DisplayName("Test delete trip by id")
    void testDeleteTripById() throws Exception {
        LocalDate departureDate = LocalDate.now().minus(1, ChronoUnit.DAYS);
        LocalTime departureTime = LocalTime.of(10, 0);
        LocalTime arrivalTime = LocalTime.of(12, 0);

        Trip trip = new Trip("Porto", "Lisboa", departureDate, departureTime, arrivalTime, 50.0, 50,
                List.of("A12", "A20", "A30", "A40", "A25"));

        when(tripService.saveTrip(any(Trip.class))).thenReturn(trip);
        when(tripService.getTripById(1)).thenReturn(trip);
        mockMvc.perform(delete("/api/trips/1")).andExpect(status().isOk());
        verify(tripService, times(1)).deleteTrip(1);
    }

    @Test
    @DisplayName("Test delete trip by id not found")
    void testDeleteTripByIdNotFound() throws Exception {
        mockMvc.perform(delete("/api/trips/1")).andExpect(status().isNotFound());
        verify(tripService, times(1)).getTripById(1);
    }
    
}
