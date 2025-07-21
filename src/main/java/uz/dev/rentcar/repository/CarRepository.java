package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

public interface CarRepository extends JpaRepository<Car, Long> {

    default Car getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with ID : " + id, HttpStatus.NOT_FOUND));
    }

}