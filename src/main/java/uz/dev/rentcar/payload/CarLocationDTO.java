package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.CarLocation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarLocationDTO implements Serializable {
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long id;
    private boolean deleted = false;
    private BigDecimal latitude;
    private BigDecimal longitude;
}