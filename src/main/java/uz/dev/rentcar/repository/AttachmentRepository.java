package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    default Attachment findByIdOrThrowException(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Attachment not found with id: " + id, HttpStatus.NOT_FOUND));

    }
}