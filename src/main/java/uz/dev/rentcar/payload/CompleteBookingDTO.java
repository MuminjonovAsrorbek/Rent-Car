package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 18:03
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompleteBookingDTO {

    private Long bookingId;

    private String userEmail;

}
