package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.CarLocation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarLocationDTO implements Serializable {

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
    @Schema(
            description = "Latitude of the car's location",
            example = "41.2995",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal latitude;

    @NotNull
    @Schema(
            description = "Longitude of the car's location",
            example = "69.2401",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal longitude;

    @NotNull
    @Schema(
            description = "ID of the car associated with this location",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long bookingId;
}