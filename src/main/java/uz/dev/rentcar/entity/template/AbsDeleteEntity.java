package uz.dev.rentcar.entity.template;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldNameConstants;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
@FieldNameConstants
public abstract class AbsDeleteEntity extends AbsLongEntity {

    private boolean deleted = false;

}
