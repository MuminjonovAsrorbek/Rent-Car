package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Payment API", description = "Payment API")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/open/payment-methods")
    @Operation(
            summary = "Get available payment methods",
            description = "This endpoint retrieves a list of available payment methods."
    )
    public List<String> getPaymentMethods() {

        return paymentService.getPaymentMethods();

    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Create a new payment",
            description = "This endpoint allows users to create a new payment. " +
                    "Only authenticated users can create payments."
    )
    public PaymentDTO createPayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payment details",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PaymentDTO.class)
                    )
            )
            @RequestBody @Valid PaymentDTO paymentDTO,
            @AuthenticationPrincipal User currentUser) {

        return paymentService.cretePayment(paymentDTO, currentUser);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get payment by ID",
            description = "This endpoint retrieves a payment by its ID. " +
                    "Only users with ADMIN role can access this endpoint."
    )
    public PaymentDTO getPaymentById(
            @Parameter(description = "ID of the payment to retrieve", example = "1")
            @PathVariable Long id) {

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

    @PutMapping("/{bookingId}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Confirm payment",
            description = "This endpoint allows an admin to confirm a payment for a booking. " +
                    "Only users with ADMIN role can access this endpoint."
    )
    public PaymentDTO confirmPayment(
            @Parameter(description = "ID of the booking to confirm payment for", example = "1")
            @PathVariable Long bookingId) {

        return paymentService.confirmPayment(bookingId);

    }

    @PutMapping("/{bookingId}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Cancel payment",
            description = "This endpoint allows a user to cancel a payment for a booking. " +
                    "Both ADMIN and USER roles can access this endpoint."
    )
    public PaymentDTO cancelPayment(
            @Parameter(description = "ID of the booking to cancel payment for", example = "1")
            @PathVariable Long bookingId,
            @AuthenticationPrincipal User currentUser) {

        return paymentService.cancelPayment(bookingId, currentUser);

    }
}
