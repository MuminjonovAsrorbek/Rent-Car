package uz.dev.rentcar.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
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
@SQLDelete(sql = "update car set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
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
    @ToString.Exclude
    private List<Booking> bookings;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Review> reviews;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CarFeature> features;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CarLocation> locations;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Favorite> favorites;

}
