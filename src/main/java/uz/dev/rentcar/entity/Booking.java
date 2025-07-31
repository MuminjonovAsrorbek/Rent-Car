package uz.dev.rentcar.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;
import uz.dev.rentcar.enums.BookingStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@SQLDelete(sql = "update booking set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class Booking extends AbsDeleteEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "pickup_office_id", nullable = false)
    private Office pickupOffice;

    @ManyToOne
    @JoinColumn(name = "return_office_id")
    private Office returnOffice;

    @Column(nullable = false)
    private LocalDateTime pickupDate;

    private LocalDateTime returnDate;

    @Column(nullable = false)
    private Boolean isForSelf = true;

    @Column(nullable = false)
    private String recipientFullName;

    @Column(nullable = false)
    private String recipientPhone;

    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

    @Column(nullable = false)
    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<BookingHistory> history = new ArrayList<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<CarLocation> locations = new ArrayList<>();

}
