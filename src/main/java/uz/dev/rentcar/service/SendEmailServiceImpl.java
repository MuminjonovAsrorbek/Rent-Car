package uz.dev.rentcar.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.exceptions.SendEmailErrorException;
import uz.dev.rentcar.payload.*;
import uz.dev.rentcar.payload.request.CancelledBookingDTO;
import uz.dev.rentcar.service.template.SendEmailService;
import uz.dev.rentcar.utils.SendEmailGaps;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 17:17
 **/

@Service
@RequiredArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {

    private final JavaMailSender javaMailSender;

    private final SendEmailGaps sendEmailGaps;

    @Override
    @EventListener
    @Async
    public void sendBookingCreationToEmail(SendEmailBookingDTO sendEmailBookingDTO) {

        String html = sendEmailGaps.generateBookingCreationHtml(sendEmailBookingDTO);

        String subject = "RentCar - Booking Notification";

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(sendEmailBookingDTO.getUserEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new SendEmailErrorException("Email error occurred: " + sendEmailBookingDTO.getUserEmail(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    @EventListener
    @Async
    public void sendEmailCancelledBooking(CancelledBookingDTO cancelledBookingDTO) {

        String html = sendEmailGaps.generateBookingCancelledHtml(cancelledBookingDTO.getBookingId(), cancelledBookingDTO.getCancelledAt());

        String subject = "RentCar - Booking Cancellation";

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(cancelledBookingDTO.getUserEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new SendEmailErrorException("Email error occurred: " + cancelledBookingDTO.getUserEmail(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    @EventListener
    @Async
    public void sendEmailConfirmBooking(ConfirmBookingDTO confirmBookingDTO) {

        String html = sendEmailGaps.generateBookingConfirm(confirmBookingDTO.getBookingId());

        String subject = "RentCar - Booking Confirmation";

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(confirmBookingDTO.getUserEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new SendEmailErrorException("Email error occurred: " + confirmBookingDTO.getUserEmail(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    @EventListener
    @Async
    public void sendEmailCompleteBooking(CompleteBookingDTO completeBookingDTO) {

        String html = sendEmailGaps.generateBookingComplete(completeBookingDTO.getBookingId());

        String subject = "RentCar - Booking Completion";

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(completeBookingDTO.getUserEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new SendEmailErrorException("Email error occurred: " + completeBookingDTO.getUserEmail(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    @Async
    @EventListener
    public void checkReturnDeadlines(ReturnDeadlineDTO dto) {

        String html = sendEmailGaps.generateBookingReturnReminder(
                dto.getBookingId(),
                dto.getCarBrandAndModel(),
                dto.getReturnDate(),
                dto.getReturnOfficeName()
        );

        String subject = "RentCar - Return Reminder";

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(dto.getUser().getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new SendEmailErrorException("Email error occurred: " + dto.getUser().getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    @Async
    @EventListener
    public void checkOverdueReturns(SendPenaltyDTO dto) {

        String html = sendEmailGaps.generateBookingOverdueReminder(
                dto
        );

        String subject = "RentCar - Overdue Return Reminder";

        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(dto.getUser().getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {

            throw new SendEmailErrorException("Email error occurred: " + dto.getUser().getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }
}