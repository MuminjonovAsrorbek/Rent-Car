package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.CancelledBookingDTO;
import uz.dev.rentcar.payload.CompleteBookingDTO;
import uz.dev.rentcar.payload.ConfirmBookingDTO;
import uz.dev.rentcar.payload.SendEmailBookingDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 17:17
 **/

public interface SendEmailService {

    void sendBookingCreationToEmail(SendEmailBookingDTO sendEmailBookingDTO);

    void sendEmailCancelledBooking(CancelledBookingDTO cancelledBookingDTO);

    void sendEmailConfirmBooking(ConfirmBookingDTO confirmBookingDTO);

    void sendEmailCompleteBooking(CompleteBookingDTO completeBookingDTO);

}
