package hw.tqs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hw.tqs.model.MarkedTrip;

import java.util.List;


public interface MarkedTripRepository extends JpaRepository<MarkedTrip, Integer>{
    public MarkedTrip findById(Long id);
    public List<MarkedTrip> findByTripID(Integer tripID);
    boolean existsById(Long id);
    void deleteById(Long id);


}
