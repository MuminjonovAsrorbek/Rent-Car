package uz.dev.rentcar.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.dev.rentcar.enums.PaymetMethodEnum;

import java.time.LocalDateTime;

@Data
public class BookingCreateDTO {

    @NotNull
    private Long carId;

    @NotNull
    private Long pickupOfficeId;

    @NotNull
    private Long returnOfficeId;

    @NotNull
    private LocalDateTime pickupDate;

    @NotNull
    private LocalDateTime returnDate;

    @NotNull
    private PaymetMethodEnum paymentMethod;

    private boolean isForSelf = true;

    private String recipientFullName;

    private String recipientPhone;

    private String promoCode;
}
