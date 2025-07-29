package uz.dev.rentcar.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import uz.dev.rentcar.entity.AuditLog;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.repository.AuditLogRepository;
import uz.dev.rentcar.utils.SecurityUtils;

@Component
public class EntityAuditListener {

    private final AuditLogRepository auditLogRepository;

    private final SecurityUtils securityUtils;

    public EntityAuditListener(@Lazy AuditLogRepository auditLogRepository, SecurityUtils securityUtils) {
        this.auditLogRepository = auditLogRepository;
        this.securityUtils = securityUtils;
    }

    @PostPersist
    public void postPersist(Object entity) {
        saveAuditLog(entity, "CREATED");
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        saveAuditLog(entity, "UPDATED");
    }

    @PreRemove
    public void preRemove(Object entity) {
        saveAuditLog(entity, "DELETED");
    }

    private void saveAuditLog(Object entity, String action) {

        AuditLog log = new AuditLog();
        log.setEmail(securityUtils.getCurrentUser().getUsername());
        log.setEntityName(entity.getClass().getSimpleName());
        log.setAction(action);

        if (entity instanceof AbsLongEntity baseEntity) {

            log.setEntityId(baseEntity.getId());
        }

        log.setDescription(action + " action on " + entity.getClass().getSimpleName());

        auditLogRepository.save(log);
    }
}
