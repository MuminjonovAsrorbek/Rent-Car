package uz.dev.rentcar.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private String brand;

    private String model;

    private int year;

    private Long pricePerDay;

    private int seats;

    private FuelTypeEnum fuelType;

    private BigDecimal fuelConsumption;

    private TransmissionEnum transmission;

    private List<String> carFeature = new ArrayList<>();

    private List<Long> categoriesIds;

    private Long mainImageId;

    private List<Long> attachmentIds;

}
