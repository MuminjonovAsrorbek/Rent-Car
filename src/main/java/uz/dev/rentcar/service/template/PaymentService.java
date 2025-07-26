package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.PaymentDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/26/25 17:09
 **/

public interface PaymentService {
    List<String> getPaymentMethods();

    PaymentDTO cretePayment(PaymentDTO paymentDTO, User currentUser);

    PaymentDTO getPaymentById(Long id);

    PaymentDTO getPaymentByBookingId(Long bookingId, User currentUser);

    List<PaymentDTO> getUserPayments(User currentUser);
}
