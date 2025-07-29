package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.payload.AuditLogDTO;
import uz.dev.rentcar.mapper.AuditLogMapper;
import uz.dev.rentcar.repository.AuditLogRepository;
import uz.dev.rentcar.service.template.AuditLogService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    private final AuditLogMapper auditLogMapper;

    @Override
    public List<AuditLogDTO> getAllLogs() {
        return auditLogMapper.toDTO(auditLogRepository.findAll());
    }

    @Override
    public List<AuditLogDTO> getLogsByEmail(String email) {
        return auditLogMapper.toDTO(auditLogRepository
                .findAll()
                .stream()
                .filter(log -> log.getEmail().equalsIgnoreCase(email))
                .toList());
    }
}