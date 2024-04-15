package hw.tqs.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hw.tqs.controller.TripController;
import hw.tqs.model.Trip;
import hw.tqs.repository.TripRepository;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class DataInitializer {

    @Autowired
    private TripRepository tripRepository;

    private static final Logger logger = LogManager.getLogger(TripController.class);


    @PostConstruct
    public void init() {
        if (tripRepository.count() == 0) {
            insertData();
        } else {
            logger.info("Trips already exists");
        }
    }

    public void insertData() {
        List<String> locations = List.of("Aveiro", "Braga", "Coimbra", "Porto", "Lisboa");
    
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(30);
    
        List<Trip> trips = new ArrayList<>();
    
        Random random = new Random();
    
        for (String origin : locations) {
            for (String destination : locations) {
                if (!origin.equals(destination)) {
                    LocalDateTime departureDate = LocalDateTime.of(startDate, LocalTime.of(10, 0)); // Horário de partida inicial
                    while (departureDate.toLocalDate().isBefore(endDate)) {
                        for (int i = 0; i < 3; i++) { 
                            LocalTime randomDepartureTime = LocalTime.of(random.nextInt(7) + 10, random.nextInt(60)); // Horário de partida aleatório entre 10h e 17h59
                            LocalDateTime departureDateTime = LocalDateTime.of(departureDate.toLocalDate(), randomDepartureTime);
                            LocalDateTime arrivalDateTime = departureDateTime.plusHours(2 + random.nextInt(5)); // Duração da viagem entre 2 e 6 horas
                            Double price = Math.round((10 + Math.random() * 100) * 100.0) / 100.0;
                            
                            List<String> randomOccupiedSeats = new ArrayList<>();
                            Set<Integer> occupiedSeatNumbers = new HashSet<>(); // Usado para verificar se o número do assento já foi adicionado

                            for (int j = 0; j < random.nextInt(50) + 2; j++) {
                                int seatNumber;
                                do {
                                    // Gera um número de assento entre 1 e 50
                                    seatNumber = random.nextInt(50) + 1;
                                } while (occupiedSeatNumbers.contains(seatNumber)); // Verifica se o número já está na lista
                                occupiedSeatNumbers.add(seatNumber); // Adiciona o número à lista de números ocupados
                                randomOccupiedSeats.add("A" + seatNumber); // Adiciona o número do assento formatado à lista de assentos ocupados
                            }

                            trips.add(new Trip(origin, destination, departureDate.toLocalDate(), randomDepartureTime, arrivalDateTime.toLocalTime(), price, 50, randomOccupiedSeats));
                        }
                        departureDate = departureDate.plusDays(1); 
                    }
                }
            }
        }

        logger.info("Inserting {} trips", trips.size());
    
        tripRepository.saveAll(trips);
    }
    
}
