package uz.dev.rentcar.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import uz.dev.rentcar.payload.SendEmailBookingDTO;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 7/28/25 16:48
 **/


@Component
@RequiredArgsConstructor
public class SendEmailGaps {

    private final SpringTemplateEngine templateEngine;

    public String generateBookingCreationHtml(SendEmailBookingDTO dto) {

        Context context = new Context();

        context.setVariable("carBrandAndModel", dto.getCarBrandAndModel());
        context.setVariable("bookingId", dto.getBookingId());
        context.setVariable("createdAt", CommonUtils.formattedDate(dto.getCreatedAt()));
        context.setVariable("totalPrice", dto.getTotalPrice());
        context.setVariable("paymentUrl", "http://localhost:8080/api/payments/" + dto.getBookingId() + "/confirm");

        return templateEngine.process("email/create-booking", context);

    }

    public String generateBookingCancelledHtml(Long id, LocalDateTime cancellationDate) {

        Context context = new Context();

        context.setVariable("bookingId", id);
        context.setVariable("cancellationDate", CommonUtils.formattedDate(cancellationDate));

        return templateEngine.process("email/cancel-booking", context);

    }

    public String generateBookingConfirm(Long bookingId) {

        Context context = new Context();

        context.setVariable("bookingId", bookingId);

        return templateEngine.process("email/confirm-booking", context);
    }

    public String generateBookingComplete(Long bookingId) {

        Context context = new Context();

        context.setVariable("bookingId", bookingId);

        return templateEngine.process("email/complete-booking", context);
    }

}