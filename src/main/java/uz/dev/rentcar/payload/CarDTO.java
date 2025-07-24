package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.enums.TransmissionEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link uz.dev.rentcar.entity.Car}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;

    @JsonIgnore
    private boolean deleted = false;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    private int year;

    @NotNull
    private Long pricePerDay;

    private boolean available = true;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageUrl;

    private String description;

    @NotNull
    private int seats;

    @NotNull
    private FuelTypeEnum fuelType;

    private BigDecimal fuelConsumption;

    @NotNull
    private TransmissionEnum transmission;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CategoryDTO> categories;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CarFeatureDTO> features;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<BookingDTO> bookings;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ReviewDTO> reviews;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AttachmentDTO> images;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CarLocationDTO> locations;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<FavoriteDTO> favorites;
}