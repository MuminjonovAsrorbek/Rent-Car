package uz.dev.rentcar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.CarFeature}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarFeatureDTO implements Serializable {

    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private boolean deleted = false;

    private String featureName;

    private Long carId;

}