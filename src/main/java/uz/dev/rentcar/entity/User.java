package uz.dev.rentcar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.rentcar.entity.template.AbsDeleteEntity;
import uz.dev.rentcar.enums.RoleEnum;

/**
 * Created by: asrorbek
 * DateTime: 7/18/25 09:29
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@FieldNameConstants
public class User extends AbsDeleteEntity {

    private String fullName;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    // davomi bor

}
