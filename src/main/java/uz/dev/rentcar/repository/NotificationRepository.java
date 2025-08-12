package uz.dev.rentcar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Notification;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    Page<Notification> findByUserId(Long id, Pageable pageable);

    List<Notification> findByUserId(Long userId);


    Page<Notification> findByUserIdAndIsReadFalse(Long id, Pageable pageable);

    default Notification findByIdOrThrowException(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Notification not found with id: " + id, HttpStatus.NOT_FOUND));

    }
}