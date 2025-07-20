package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.CarImage;

public interface CarImageRepository extends JpaRepository<CarImage, Long> {
}