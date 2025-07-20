package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}