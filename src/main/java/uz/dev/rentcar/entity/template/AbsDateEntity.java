package uz.dev.rentcar.entity.template;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.dev.rentcar.listener.EntityAuditListener;

import java.sql.Timestamp;

/**
 * Created by: asrorbek
 * DateTime: 7/18/25 09:35
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
@FieldNameConstants
@EntityListeners(EntityAuditListener.class)
public class AbsDateEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
