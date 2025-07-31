package uz.dev.rentcar.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Notification;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByUserId(Long id, Sort sort);


    List<Notification> findByUserIdAndIsReadFalse(Long id, Sort sort);

    default Notification findByIdOrThrowException(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Notification not found with id: " + id, HttpStatus.NOT_FOUND));

    }
}