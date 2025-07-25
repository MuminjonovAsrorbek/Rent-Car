package uz.dev.rentcar.payload;

import lombok.Data;
import uz.dev.rentcar.enums.BookingStatusEnum;
import java.time.LocalDateTime;

@Data
public class BookingDTO {

    private Long id;

    private Long userId;

    private Long carId;

    private String carInfo;

    private LocalDateTime pickupDate;

    private LocalDateTime returnDate;

    private Long totalPrice;

    private BookingStatusEnum status;

    private LocalDateTime createdAt;

}