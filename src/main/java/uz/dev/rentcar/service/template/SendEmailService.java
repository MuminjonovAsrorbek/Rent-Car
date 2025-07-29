package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.*;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 17:17
 **/

public interface SendEmailService {

    void sendBookingCreationToEmail(SendEmailBookingDTO sendEmailBookingDTO);

    void sendEmailCancelledBooking(CancelledBookingDTO cancelledBookingDTO);

    void sendEmailConfirmBooking(ConfirmBookingDTO confirmBookingDTO);

    void sendEmailCompleteBooking(CompleteBookingDTO completeBookingDTO);

    void checkReturnDeadlines(ReturnDeadlineDTO dto);

    void checkOverdueReturns(SendPenaltyDTO dto);

}
