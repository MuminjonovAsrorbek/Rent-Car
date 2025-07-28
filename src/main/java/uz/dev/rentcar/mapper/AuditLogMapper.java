package uz.dev.rentcar.mapper;

import org.mapstruct.Mapper;
import uz.dev.rentcar.entity.AuditLog;
import uz.dev.rentcar.payload.AuditLogDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 16:09
 **/

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    AuditLogDTO toDTO(AuditLog auditLog);

    List<AuditLogDTO> toDTO(List<AuditLog> auditLogs);

}
