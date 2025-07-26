package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.enums.TransmissionEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link uz.dev.rentcar.entity.Booking}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long userId;

    private String userFullName;

    private Long carId;

    private String carBrand;

    private String carModel;

    private int carSeats;

    private FuelTypeEnum carFuelType;

    private BigDecimal carFuelConsumption;

    private TransmissionEnum carTransmission;

    private Long pickupOfficeId;

    private String pickupOfficeName;

    private String pickupOfficeAddress;

    private Long returnOfficeId;

    private String returnOfficeName;

    private String returnOfficeAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime pickupDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime returnDate;

    private Boolean isForSelf;

    private String recipientFullName;

    private String recipientPhone;

    private Boolean hasPromoCode;

    private Long totalPrice;

    private BookingStatusEnum status;

    private PaymentDTO payment;

    private List<CarLocationDTO> locations = new ArrayList<>();
}