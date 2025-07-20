package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.CarFeature;
import uz.dev.rentcar.entity.Office;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    default Office getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Office not found with ID : " + id, HttpStatus.NOT_FOUND));
    }
}