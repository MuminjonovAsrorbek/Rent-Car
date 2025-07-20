package uz.dev.rentcar.entity.template;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
@FieldNameConstants
public abstract class AbsLongEntity extends AbsDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

}
