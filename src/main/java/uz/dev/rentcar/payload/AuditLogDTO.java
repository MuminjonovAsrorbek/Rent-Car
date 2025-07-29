package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link uz.dev.rentcar.entity.AuditLog}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDTO implements Serializable {

    private Long id;

    private String email;

    private String entityName;

    private Long entityId;

    private String action;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}