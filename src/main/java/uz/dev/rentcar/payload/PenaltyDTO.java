package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.entity.Penalty;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link Penalty}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PenaltyDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp penaltyDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;

    private Long bookingId;

    private Long penaltyAmount;

    private Long overdueDays;

}