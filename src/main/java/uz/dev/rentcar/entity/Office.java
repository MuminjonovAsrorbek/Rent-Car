package uz.dev.rentcar.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Office extends AbsDeleteEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "pickupOffice", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Booking> pickupBookings;

    @OneToMany(mappedBy = "returnOffice", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Booking> returnBookings;

}