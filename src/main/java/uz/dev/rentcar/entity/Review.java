package uz.dev.rentcar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
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
public class Review extends AbsDeleteEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;

    private int rating;

    private String comment;

}
