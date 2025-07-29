package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Penalty;

import java.util.Optional;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {


    Optional<Penalty> findByBookingId(Long id);
}