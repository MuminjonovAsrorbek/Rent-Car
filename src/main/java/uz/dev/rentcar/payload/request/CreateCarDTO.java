package uz.dev.rentcar.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @NotNull
    private BigDecimal fuelConsumption;

    @NotNull
    private List<String> carFeature = new ArrayList<>();

    @NotNull
    private TransmissionEnum transmission;

    @NotNull
    private List<Long> categoriesIds;

}
