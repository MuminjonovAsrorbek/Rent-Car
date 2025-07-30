package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.PaymetMethodEnum;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 16:53
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendEmailBookingDTO {

    private String userEmail;

    private String carBrandAndModel;

    private Long bookingId;

    private LocalDateTime createdAt;

    private Long totalPrice;

    private PaymetMethodEnum paymentMethod;

}
