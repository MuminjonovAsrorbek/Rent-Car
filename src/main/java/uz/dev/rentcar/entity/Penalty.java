package uz.dev.rentcar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.dev.rentcar.enums.PenaltyStatusEnum;

import java.sql.Timestamp;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 13:21
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@FieldNameConstants
public class Penalty {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp penaltyDate;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PenaltyStatusEnum status;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private Long penaltyAmount;

    @Column(nullable = false)
    private Long overdueDays;
}

