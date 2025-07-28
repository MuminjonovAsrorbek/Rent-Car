package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    private Long totalPrice;

}
