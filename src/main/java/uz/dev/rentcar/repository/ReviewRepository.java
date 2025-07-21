package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Review;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByCarId(Long carId);

    default Review getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(()->new EntityNotFoundException("Review not found with id : "+id, HttpStatus.NOT_FOUND));
    }
}