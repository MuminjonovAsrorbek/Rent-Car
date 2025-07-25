package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}