package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.CarLocation;

public interface CarLocationRepository extends JpaRepository<CarLocation, Long> {
}