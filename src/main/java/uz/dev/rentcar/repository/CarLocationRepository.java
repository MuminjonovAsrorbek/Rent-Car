package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.CarLocation;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;

public interface CarLocationRepository extends JpaRepository<CarLocation, Long> {

    default CarLocation getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("CarLocation not found with ID : " + id, HttpStatus.NOT_FOUND)
                );
    }

    List<CarLocation> findByBookingId(Long id);
}