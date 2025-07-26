package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;

public interface AuditLogService {

    void logLogin(String email, boolean isSuccess);

    void logRegister(String email);

    void logAction(User currentUser, String entityName, Long entityId, String action, String description);
}