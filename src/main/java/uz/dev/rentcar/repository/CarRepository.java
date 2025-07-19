package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

    default Car getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

}