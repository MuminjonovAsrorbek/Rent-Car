package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.entity.User;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 13:02
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnDeadlineDTO {

    private Long bookingId;

    private LocalDateTime returnDate;

    private User user;

    private String carBrandAndModel;

    private String returnOfficeName;
}
