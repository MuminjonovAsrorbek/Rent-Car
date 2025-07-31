package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.PaymentStatus;
import uz.dev.rentcar.enums.PaymetMethodEnum;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.Payment}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonIgnore
    private boolean deleted = false;

    @NotNull
    private Long bookingId;

    @NotNull
    private Long amount;

    @NotNull
    private PaymetMethodEnum paymentMethod;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PaymentStatus status;
}