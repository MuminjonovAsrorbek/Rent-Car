package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.PaymentDTO;
import uz.dev.rentcar.service.template.PaymentService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/26/25 17:08
 **/

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/open/payment-methods")
    public List<String> getPaymentMethods() {

        return paymentService.getPaymentMethods();

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public PaymentDTO createPayment(@RequestBody @Valid PaymentDTO paymentDTO,
                                    @AuthenticationPrincipal User currentUser) {

        return paymentService.cretePayment(paymentDTO, currentUser);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PaymentDTO getPaymentById(@PathVariable Long id) {

        return paymentService.getPaymentById(id);

    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public PaymentDTO getPaymentByBookingId(@PathVariable Long bookingId,
                                            @AuthenticationPrincipal User currentUser) {

        return paymentService.getPaymentByBookingId(bookingId, currentUser);

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public List<PaymentDTO> getUserPayments(@AuthenticationPrincipal User currentUser) {

        return paymentService.getUserPayments(currentUser);

    }
}
