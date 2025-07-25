package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Booking;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUserId(Long userId);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.car.id = :carId AND b.status IN :statuses AND b.pickupDate < :returnDate AND b.returnDate > :pickupDate")
    boolean existsOverlappingBooking(
            @Param("carId") Long carId,
            @Param("pickupDate") LocalDateTime pickupDate,
            @Param("returnDate") LocalDateTime returnDate,
            @Param("statuses") List<BookingStatusEnum> statuses);

    default Booking getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with ID : " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    boolean existsById(Long aLong);
}