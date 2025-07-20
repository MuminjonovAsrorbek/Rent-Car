package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}