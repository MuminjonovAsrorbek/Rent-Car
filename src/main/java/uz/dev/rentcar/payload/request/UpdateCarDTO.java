package uz.dev.rentcar.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.FuelTypeEnum;
import uz.dev.rentcar.enums.TransmissionEnum;

import java.math.BigDecimal;
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

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotNull
    private int year;

    @NotNull
    private Long pricePerDay;

    @NotNull
    private int seats;

    @NotNull
    private FuelTypeEnum fuelType;

    private BigDecimal fuelConsumption;

    @NotNull
    private TransmissionEnum transmission;

    @NotNull
    private List<Long> categoriesIds;

    private Long mainImageId;

    private List<Long> attachmentIds;

}
