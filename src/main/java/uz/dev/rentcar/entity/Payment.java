package uz.dev.rentcar.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;
import uz.dev.rentcar.enums.PaymentStatus;
import uz.dev.rentcar.enums.PaymetMethodEnum;

/**
 * Created by: asrorbek
 * DateTime: 7/18/25 09:51
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Payment extends AbsDeleteEntity {

    @ManyToOne
    private Booking booking;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymetMethodEnum paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}
