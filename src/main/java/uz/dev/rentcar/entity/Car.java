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

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private int year;

    @Column(nullable = false)
    private Long pricePerDay;

    private boolean available = true;

    private String imageUrl;

    private String description;

    @Column(nullable = false)
    private int seats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelTypeEnum fuelType;

    private BigDecimal fuelConsumption;

    @Column(nullable = false)
    private String transmission;

    @ManyToMany
    @JoinTable(
            name = "car_categories",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ToString.Exclude
    private List<Category> categories;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarImage> images;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarFeature> features;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CarLocation> locations;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

}
