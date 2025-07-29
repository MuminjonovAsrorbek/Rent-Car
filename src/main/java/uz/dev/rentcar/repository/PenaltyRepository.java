package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.entity.Penalty;
import uz.dev.rentcar.enums.PenaltyStatusEnum;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {

    Optional<Penalty> findByBookingId(Long id);

    List<Penalty> findByBookingUserId(Long id);

    default Penalty findByBookingIdOrThrowException(Long bookingId) {

        return this.findByBookingId(bookingId).orElseThrow(
                () -> new EntityNotFoundException("Penalty not found for booking ID: " + bookingId, HttpStatus.NOT_FOUND)
        );

    }

    default Penalty findByIdOrThrowException(Long id) {

        return findById(id).orElseThrow(
                () -> new EntityNotFoundException("Penalty not found for ID: " + id, HttpStatus.NOT_FOUND)
        );

    }

    List<Penalty> findByBookingUserIdAndStatus(Long id, PenaltyStatusEnum penaltyStatusEnum);
}