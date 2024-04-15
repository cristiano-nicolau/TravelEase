package hw.tqs.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import hw.tqs.data.DataInitializer;
import hw.tqs.model.Trip;
import hw.tqs.repository.TripRepository;

@SpringBootTest
class DataInitializerTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void testInsertData() {
        doAnswer(invocation -> {
            List<Trip> trips = invocation.getArgument(0);
            assertEquals(1800, trips.size()); 
            return null;
        }).when(tripRepository).saveAll(anyList());


        dataInitializer.insertData();

        verify(tripRepository, times(1)).saveAll(anyList());
    }
}
