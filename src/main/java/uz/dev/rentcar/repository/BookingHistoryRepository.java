package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.BookingHistory;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
}