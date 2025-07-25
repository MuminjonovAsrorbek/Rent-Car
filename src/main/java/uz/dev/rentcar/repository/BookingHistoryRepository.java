package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.BookingHistory;
import java.util.List;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
    List<BookingHistory> findAllByBookingIdOrderByCreatedAtAsc(Long bookingId);
}