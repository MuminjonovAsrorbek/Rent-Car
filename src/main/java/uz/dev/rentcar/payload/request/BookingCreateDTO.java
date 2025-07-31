package uz.dev.rentcar.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import uz.dev.rentcar.enums.PaymetMethodEnum;

import java.time.LocalDateTime;

@Data
public class BookingCreateDTO {

    @NotNull
    @Schema(description = "ID of the car being booked", example = "1")
    private Long carId;

    @NotNull
    @Schema(description = "ID of the pickup office", example = "1")
    private Long pickupOfficeId;

    @NotNull
    @Schema(description = "ID of the return office", example = "2")
    private Long returnOfficeId;

    @NotNull
    @Schema(description = "Date and time when the car will be picked up", example = "2025-08-01T10:00")
    private LocalDateTime pickupDate;

    @NotNull
    @Schema(description = "Date and time when the car will be returned", example = "2025-08-10T10:00")
    private LocalDateTime returnDate;

    @NotNull
    @Schema(description = "Payment method for the booking", example = "CREDIT_CARD")
    private PaymetMethodEnum paymentMethod;

    @Schema(description = "ID of the user making the booking", example = "true")
    private boolean isForSelf = true;

    @Schema(description = "Full name of the recipient if booking is not for self", example = "Jane Doe")
    private String recipientFullName;

    @Schema(description = "Phone number of the recipient if booking is not for self", example = "+998901234567")
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must start with +998 and contain 13 digits")
    private String recipientPhone;

    @Schema(description = "Promo code applied to the booking", example = "SUMMER2025")
    private String promoCode;
}
