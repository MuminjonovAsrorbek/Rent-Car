package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long userId;

    private String message;

    private NotificationTypeEnum type;

    private boolean isRead = false;
}