package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.SendEmailBookingDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 17:17
 **/

public interface SendEmailService {

    void sendBookingCreationToEmail(SendEmailBookingDTO sendEmailBookingDTO);

}
