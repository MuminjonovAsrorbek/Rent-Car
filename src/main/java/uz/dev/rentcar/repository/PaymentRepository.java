package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Payment;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    boolean existsByBookingId(Long id);

    default Payment getByIdOrThrow(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found with id : " + id, HttpStatus.NOT_FOUND));

    }

    Optional<Payment> findByBookingId(Long bookingId);

    default Payment findByBookingIdOrThrowException(Long bookingId) {

        return this.findByBookingId(bookingId).orElseThrow(() -> new EntityNotFoundException("Payment not found with booking id : " + bookingId, HttpStatus.NOT_FOUND));

    }

    List<Payment> findByBookingUserId(Long id);
}