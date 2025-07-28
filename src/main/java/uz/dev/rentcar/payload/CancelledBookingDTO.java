package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 17:33
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CancelledBookingDTO {

    private Long bookingId;

    private LocalDateTime cancelledAt;

    private String userEmail;

}
