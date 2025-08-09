package uz.dev.rentcar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Review;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByCarId(Long carId, Pageable pageable);

    default Review getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id : " + id, HttpStatus.NOT_FOUND));
    }
}