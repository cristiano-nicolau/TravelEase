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

import hw.tqs.model.*;
import hw.tqs.services.MarkedTripService;
import hw.tqs.services.TripService;

@WebMvcTest(MarkedTripController.class)
public class MarkedTripControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MarkedTripService markedTripService;

    @MockBean
    private TripService tripService;

    @Test
    @DisplayName("Test get marked trips")
    void testGetMarkedTrips() throws Exception {
        Trip trip1 = new Trip("Porto", "Lisboa", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50, List.of("A12", "A20", "A30", "A40", "A25"));
        List<MarkedTrip> markedTrips = List.of(
            new MarkedTrip(trip1.getId(), 1,2, List.of("A15", "A22", "A31"), "John Doe", "johndoe@example.com", "123456789", "123456789", "123 Main St", "City", "12345", "Visa", "1234567890123456"),
            new MarkedTrip(trip1.getId(), 1, 2, List.of("A10", "A15", "A20"), "Jane Doe", "janedoe@example.com", "987654321", "987654321", "456 Main St", "City", "54321", "Mastercard", "9876543210987654"));

        when(markedTripService.getMarkedTrips()).thenReturn(markedTrips);
        mvc.perform(get("/api/mark/trip/")).andExpect(status().isOk());
        verify(markedTripService, times(1)).getMarkedTrips();
    }

    @Test
    @DisplayName("Test get marked trips but none found")
    void testGetMarkedTripsButNoneFound() throws Exception {
        when(markedTripService.getMarkedTrips()).thenReturn(List.of());
        mvc.perform(get("/api/mark/trip/")).andExpect(status().isNotFound());
        verify(markedTripService, times(1)).getMarkedTrips();
    }

    @Test
    @DisplayName("Test get marked trip by ID")
    void testGetMarkedTripById() throws Exception {
        // Criação de dados de teste
        Trip trip = new Trip("Porto", "Lisboa", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50, List.of("A12", "A20", "A30", "A40", "A25"));
        MarkedTrip markedTrip = new MarkedTrip(trip.getId(), 1,2, List.of("A15", "A22", "A31"), "John Doe", "johndoe@example.com", "123456789", "123456789", "123 Main St", "City", "12345", "Visa", "1234567890123456");
        long id = 1;
        String idString = Long.toString(id);

        when(markedTripService.getMarkedTripById(id)).thenReturn(markedTrip);

        mvc.perform(get("/api/mark/trip/" + idString))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numPassagersAdults").value(markedTrip.getNumPassagersAdults()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numPassagersChildren").value(markedTrip.getNumPassagersChildren()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seats").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(markedTrip.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(markedTrip.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nif").value(markedTrip.getNif()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber").value(markedTrip.getCardNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(markedTrip.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value(markedTrip.getCity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.zipCode").value(markedTrip.getZipCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardType").value(markedTrip.getCardType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(markedTrip.getPhone()));

        verify(markedTripService, times(1)).getMarkedTripById(id);
    }

    @Test
    @DisplayName("Test get marked trip by ID but none found")
    void testGetMarkedTripByIdButNoneFound() throws Exception {
        long id = 1;
        String idString = Long.toString(id);

        when(markedTripService.getMarkedTripById(id)).thenReturn(null);

        mvc.perform(get("/api/mark/trip/" + idString)).andExpect(status().isNotFound());

        verify(markedTripService, times(1)).getMarkedTripById(id);
    }

    @Test
    @DisplayName("Test get marked trips by trip ID")
    void testGetMarkedTripsByTripID() throws Exception {
        // Criação de dados de teste
        Trip trip = new Trip("Porto", "Lisboa", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50, List.of("A12", "A20", "A30", "A40", "A25"));
        MarkedTrip markedTrip = new MarkedTrip(1, 1,2, List.of("A15", "A22", "A31"), "John Doe", "johndoe@example.com", "123456789", "123456789", "123 Main St", "City", "12345", "Visa", "1234567890123456");
        long id = 1;
        int idInt = 1;

        when(markedTripService.getMarkedTripsByTripID(idInt)).thenReturn(List.of(markedTrip));

        mvc.perform(get("/api/mark/trip/trip/" + idInt))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numPassagersAdults").value(markedTrip.getNumPassagersAdults()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numPassagersChildren").value(markedTrip.getNumPassagersChildren()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].seats").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(markedTrip.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(markedTrip.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nif").value(markedTrip.getNif()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cardNumber").value(markedTrip.getCardNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value(markedTrip.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value(markedTrip.getCity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].zipCode").value(markedTrip.getZipCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cardType").value(markedTrip.getCardType()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value(markedTrip.getPhone()));

        verify(markedTripService, times(1)).getMarkedTripsByTripID(idInt);
    }

    @Test
    @DisplayName("Test get marked trips by trip ID but none found")
    void testGetMarkedTripsByTripIDButNoneFound() throws Exception {
        int id = 1;
        String idString = Long.toString(id);

        when(markedTripService.getMarkedTripsByTripID(id)).thenReturn(List.of());

        mvc.perform(get("/api/mark/trip/trip/" + idString)).andExpect(status().isNotFound());

        verify(markedTripService, times(1)).getMarkedTripsByTripID(id);
    }


    @Test
    @DisplayName("Test delete marked trip")
    void testDeleteMarkedTrip() throws Exception {
        String id = "1"; // Example ID
        when(markedTripService.existsMarkedTrip(Long.parseLong(id))).thenReturn(true);
        mvc.perform(delete("/api/mark/trip/" + id)).andExpect(status().isOk());
        verify(markedTripService, times(1)).deleteMarkedTrip(Long.parseLong(id));
        verify(markedTripService, times(1)).existsMarkedTrip(Long.parseLong(id));
    }

    @Test
    @DisplayName("Test delete marked trip but none found")
    void testDeleteMarkedTripButNoneFound() throws Exception {
        String id = "1"; // Example ID
        mvc.perform(delete("/api/mark/trip/" + id)).andExpect(status().isNotFound());
        verify(markedTripService, times(1)).existsMarkedTrip(Long.parseLong(id));
    }

    @Test
    @DisplayName("Test create marked trip")
    void testCreateMarkedTrip() throws Exception {
        // Criação de dados de teste
        Trip trip = new Trip("Porto", "Lisboa", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 50.0, 50, List.of("A12", "A20", "A30", "A40", "A25"));
        trip.setId(1);
        MarkedTrip markedTrip = new MarkedTrip(trip.getId(), 1,2, List.of("A15", "A22", "A31"), "John Doe", "johndoe@example.com", "123456789", "123456789", "123 Main St", "City", "12345", "Visa", "1234567890123456");

        when(tripService.getTripById(trip.getId())).thenReturn(trip);
        when(markedTripService.saveMarkedTrip(any(MarkedTrip.class))).thenReturn(markedTrip);

        // Criação de JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(markedTrip);

        mvc.perform(post("/api/mark/trip/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        verify(markedTripService, times(1)).saveMarkedTrip(any(MarkedTrip.class));
    }
}
