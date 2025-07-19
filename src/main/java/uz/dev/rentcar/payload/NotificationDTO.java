package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.NotificationTypeEnum;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.Notification}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO implements Serializable {
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long id;
    private boolean deleted = false;
    private Long userId;
    private String message;
    private NotificationTypeEnum type;
    private boolean isRead = false;
}