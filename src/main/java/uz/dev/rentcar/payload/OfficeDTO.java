package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link uz.dev.rentcar.entity.Office}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;

    @JsonIgnore
    private boolean deleted = false;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<BookingDTO> pickupBookings;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<BookingDTO> returnBookings;
}