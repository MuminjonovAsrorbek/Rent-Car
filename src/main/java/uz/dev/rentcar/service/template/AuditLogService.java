package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.AuditLogDTO;

import java.util.List;

public interface AuditLogService {
    List<AuditLogDTO> getAllLogs();
    List<AuditLogDTO> getLogsByEmail(String email);
}