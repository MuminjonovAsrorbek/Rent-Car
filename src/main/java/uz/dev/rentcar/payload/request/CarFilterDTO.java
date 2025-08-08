package uz.dev.rentcar.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.enums.TransmissionEnum;

/**
 * Created by: asrorbek
 * DateTime: 7/25/25 14:24
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarFilterDTO {

    @Schema(
            description = "Brand of the car",
            example = "Toyota"
    )
    private String brand;

    @Schema(
            description = "Price per day to rent the car",
            example = "50000"
    )
    private Long pricePerDayFrom;

    @Schema(
            description = "Price per day to rent the car",
            example = "100000"
    )
    private Long pricePerDayTo;

    @Schema(
            description = "Number of seats in the car",
            example = "4"
    )
    private Integer seats;

    @Schema(
            description = "Fuel type of the car",
            example = "PETROL"
    )
    private FuelTypeEnum fuelType;

    @Schema(
            description = "Transmission type of the car",
            example = "AUTOMATIC"
    )
    private TransmissionEnum transmission;

    @Schema(
            description = "ID of the car category",
            example = "1"
    )
    private Long categoryId;
}
