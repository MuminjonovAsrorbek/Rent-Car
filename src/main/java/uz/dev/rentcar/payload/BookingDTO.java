package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.BookingStatusEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * DTO for {@link uz.dev.rentcar.entity.Booking}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO implements Serializable {
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long id;
    private boolean deleted = false;
    private Long userId;
    private LocalDateTime pickupDate;
    private LocalDateTime returnDate;
    private boolean isForSelf = true;
    private String recipientFullName;
    private String recipientPhone;
    private Long totalPrice;
    private BookingStatusEnum status;
}