package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.UserAddress}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO implements Serializable {
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long id;
    private boolean deleted = false;
    private Long userId;
    private String addressLine;
    private String city;
    private String country;
    private String postalCode;
}