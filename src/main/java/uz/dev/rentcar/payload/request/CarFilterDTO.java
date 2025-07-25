package uz.dev.rentcar.payload.request;

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

    private String brand;

    private Long pricePerDayFrom;

    private Long pricePerDayTo;

    private Integer seats;

    private FuelTypeEnum fuelType;

    private TransmissionEnum transmission;

    private Long categoryId;
}
