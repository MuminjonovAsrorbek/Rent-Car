package uz.dev.rentcar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;

/**
 * Created by: asrorbek
 * DateTime: 7/18/25 09:49
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
@SQLDelete(sql = "update review set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class Review extends AbsDeleteEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private int rating;

    private String comment;

}
