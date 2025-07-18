package uz.dev.rentcar.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;
import uz.dev.rentcar.enums.FuelTypeEnum;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/18/25 09:37
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Car extends AbsDeleteEntity {

    private String brand;

    private String model;

    private int year;

    private Long pricePerDay;

    private boolean available = true;

    private String imageUrl;

    private String description;

    private int seats;

    @Enumerated(EnumType.STRING)
    private FuelTypeEnum fuelType;

    private BigDecimal fuelConsumption;

    private String transmission;

    @ManyToMany
    @JoinTable(
            name = "car_categories",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ToString.Exclude
    private List<Category> categories;

    // davomi bor
}
