package uz.dev.rentcar.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;
import uz.dev.rentcar.enums.BookingStatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
@SQLDelete(sql = "update booking_history set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class BookingHistory extends AbsDeleteEntity {

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status;

}