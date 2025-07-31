package uz.dev.rentcar.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.enums.TransmissionEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 14:18
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCarDTO {

    @NotBlank
    @Schema(
            description = "Brand of the car",
            example = "Toyota",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String brand;

    @NotBlank
    @Schema(
            description = "Model of the car",
            example = "Camry",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String model;

    @NotNull
    @Schema(
            description = "Year of manufacture of the car",
            example = "2023",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int year;

    @NotNull
    @Schema(
            description = "Price per day for renting the car",
            example = "50000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long pricePerDay;

    private boolean available = true;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageUrl;

    @Schema(
            description = "Description of the car",
            example = "A comfortable and spacious sedan with advanced safety features.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @NotNull
    @Schema(
            description = "Number of seats in the car",
            example = "4",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private int seats;

    @NotNull
    @Schema(
            description = "Fuel type of the car",
            example = "PETROL",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private FuelTypeEnum fuelType;

    @NotNull
    @Schema(
            description = "Fuel consumption of the car in liters per 100 km",
            example = "8.5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private BigDecimal fuelConsumption;

    @NotNull
    @Schema(
            description = "Feature type of the car",
            example = "[\"GPS\", \"Air Conditioning\", \"Leather Seats\"]",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<String> carFeature = new ArrayList<>();

    @NotNull
    @Schema(
            description = "Transmission type of the car",
            example = "AUTOMATIC",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TransmissionEnum transmission;

    @NotNull
    @Schema(
            description = "List of category IDs the car belongs to",
            example = "[1, 2, 3]",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<Long> categoriesIds;

}
