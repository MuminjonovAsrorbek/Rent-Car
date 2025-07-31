package uz.dev.rentcar.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * DateTime: 7/23/25 16:53
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCarDTO {

    @Schema(
            description = "Brand of the car",
            example = "Toyota"
    )
    private String brand;

    @Schema(
            description = "Model of the car",
            example = "Camry"
    )
    private String model;

    @Schema(
            description = "Year of manufacture of the car",
            example = "2023"
    )
    private int year;

    @Schema(
            description = "Price per day for renting the car",
            example = "50000"
    )
    private Long pricePerDay;

    @Schema(
            description = "Number of seats in the car",
            example = "4"
    )
    private int seats;

    @Schema(
            description = "Fuel type of the car",
            example = "PETROL"
    )
    private FuelTypeEnum fuelType;

    @Schema(
            description = "Fuel consumption of the car in liters per 100 km",
            example = "8.5"
    )
    private BigDecimal fuelConsumption;

    @Schema(
            description = "Transmission type of the car",
            example = "AUTOMATIC"
    )
    private TransmissionEnum transmission;

    @Schema(
            description = "Feature type of the car",
            example = "[\"GPS\", \"Air Conditioning\", \"Leather Seats\"]"
    )
    private List<String> carFeature = new ArrayList<>();

    @Schema(
            description = "List of category IDs the car belongs to",
            example = "[1, 2, 3]"
    )
    private List<Long> categoriesIds;

    @Schema(
            description = "ID of the main image for the car",
            example = "1"
    )
    private Long mainImageId;

    @Schema(
            description = "List of attachment IDs for the car",
            example = "[2, 3]"
    )
    private List<Long> attachmentIds;

}
