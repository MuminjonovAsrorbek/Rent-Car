package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.entity.User;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 13:52
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendPenaltyDTO {

    private User user;

    private Long bookingId;

    private String carBrandAndModel;

    private LocalDateTime returnDate;

    private String returnOfficeName;

    private Long penaltyAmount;

    private Long overdueDays;

}
