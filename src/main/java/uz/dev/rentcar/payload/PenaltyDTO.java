package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.entity.Penalty;
import uz.dev.rentcar.enums.PenaltyStatusEnum;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Timestamp penaltyDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Timestamp updatedAt;

    private Long bookingId;

    private Long penaltyAmount;

    private Long overdueDays;

    private PenaltyStatusEnum status;
}