package uz.dev.rentcar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;
import uz.dev.rentcar.enums.BookingStatusEnum;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/18/25 09:42
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Booking extends AbsDeleteEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Office pickupOffice;

    @ManyToOne
    private Office returnOffice;

    private LocalDateTime pickupDate;

    private LocalDateTime returnDate;

    private boolean isForSelf = true;

    private String recipientFullName;

    private String recipientPhone;

    @ManyToOne
    private PromoCode promoCode;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status;

    // davomi bor

}
