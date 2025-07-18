package uz.dev.rentcar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;
import uz.dev.rentcar.enums.NotificationTypeEnum;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Notification extends AbsDeleteEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private NotificationTypeEnum type;

    private boolean isRead = false;

}