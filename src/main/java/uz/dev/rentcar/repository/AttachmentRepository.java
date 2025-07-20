package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}