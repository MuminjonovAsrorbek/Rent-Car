package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.AuditLog;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.repository.AuditLogRepository;
import uz.dev.rentcar.service.template.AuditLogService;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void logLogin(String email, boolean isSuccess) {
        String action = isSuccess ? "LOGIN_SUCCESS" : "LOGIN_FAILURE";
        String description = isSuccess ? "User successfully logged in" : "Failed login attempt";

        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setEntityName("UserAuthentication");
        log.setAction(action);
        log.setDescription(description);
        log.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    @Override
    public void logRegister(String email) {
        AuditLog log = new AuditLog();
        log.setEmail(email);
        log.setEntityName("User");
        log.setAction("REGISTER");
        log.setDescription("New user registered with email: " + email);
        log.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    @Override
    public void logAction(User currentUser, String entityName, Long entityId, String action, String description) {
        AuditLog log = new AuditLog();

        log.setEmail(currentUser != null ? currentUser.getEmail() : "SYSTEM");

        log.setEntityName(entityName);
        log.setEntityId(entityId);
        log.setAction(action.toUpperCase());
        log.setDescription(description);
        log.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(log);
    }
}