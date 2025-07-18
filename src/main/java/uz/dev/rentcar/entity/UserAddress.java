package uz.dev.rentcar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class UserAddress extends AbsDeleteEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String addressLine;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    private String postalCode;

}