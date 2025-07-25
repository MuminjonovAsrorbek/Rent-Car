package uz.dev.rentcar.payload;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingCreateDTO {

        private Long carId;

        private Long pickupOfficeId;

        private Long returnOfficeId;

        private LocalDateTime pickupDate;

        private LocalDateTime returnDate;

        private boolean isForSelf = true;

        private String recipientFullName;

        private String recipientPhone;

        private String promoCode;
}
