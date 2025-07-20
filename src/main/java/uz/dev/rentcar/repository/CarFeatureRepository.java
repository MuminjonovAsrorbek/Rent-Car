package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.CarFeature;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

public interface CarFeatureRepository extends JpaRepository<CarFeature, Long> {

    default CarFeature getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CarFeature not found with ID : " + id, HttpStatus.NOT_FOUND));
    }

}