package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.FuelTypeEnum;

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
public class CarDTO implements Serializable {
    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private boolean deleted = false;

    private String brand;

    private String model;

    private int year;

    private Long pricePerDay;

    private boolean available = true;

    private String imageUrl;

    private String description;

    private int seats;

    private FuelTypeEnum fuelType;

    private BigDecimal fuelConsumption;

    private String transmission;

    private List<CategoryDTO> categories;

    private List<CarFeatureDTO> features;

    //davomi bor
}