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
import uz.dev.rentcar.payload.SendEmailBookingDTO;
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
}
