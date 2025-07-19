package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}