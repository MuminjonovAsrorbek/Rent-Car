package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.BookingDTO;
import uz.dev.rentcar.payload.request.BookingCreateDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.BookingService;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking API", description = "API for managing car bookings")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    @Operation(
            summary = "Create a new booking",
            description = "This endpoint allows users to create a new booking for a car. " +
                    "The request body must contain the necessary details for the booking."
    )
    public BookingDTO createBooking(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Booking creation request body",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingCreateDTO.class)
                    )
            )
            @RequestBody BookingCreateDTO dto,
            @AuthenticationPrincipal User currentUser) {

        return bookingService.createBooking(dto, currentUser);

    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    @Operation(
            summary = "Get all bookings for the current user",
            description = "This endpoint retrieves all bookings made by the currently authenticated user."
    )
    public PageableDTO getMyBookings(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                     @AuthenticationPrincipal User currentUser) {

        return bookingService.getMyBookings(currentUser, page, size);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    @Operation(
            summary = "Get booking by ID",
            description = "This endpoint retrieves a booking by its ID for the currently authenticated user."
    )
    public BookingDTO getBookingById(
            @Parameter(description = "ID of the booking to retrieve", example = "1")
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return bookingService.getBookingById(id, currentUser);
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    @Operation(
            summary = "Cancel a booking",
            description = "This endpoint allows users to cancel a booking by its ID. " +
                    "Only the user who made the booking or an admin can cancel it."
    )
    public BookingDTO cancelBooking(
            @Parameter(description = "ID of the booking to cancel", example = "1")
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {

        return bookingService.cancelBooking(id, currentUser);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all bookings by user ID",
            description = "This endpoint retrieves all bookings made by a specific user identified by their ID. " +
                    "Only accessible to admins."
    )
    public PageableDTO getBookingsByUserId(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "ID of the user whose bookings to retrieve", example = "1")
            @PathVariable Long userId) {

        return bookingService.getBookingsByUserId(userId, page, size);

    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Confirm a booking",
            description = "This endpoint allows an admin to confirm a booking by its ID. " +
                    "Only accessible to admins."
    )
    public BookingDTO confirmBooking(
            @Parameter(description = "ID of the booking to confirm", example = "1")
            @PathVariable Long id) {

        return bookingService.confirmBooking(id);

    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Complete a booking",
            description = "This endpoint allows an admin to mark a booking as completed by its ID. " +
                    "Only accessible to admins."
    )
    public BookingDTO completeBooking(
            @Parameter(description = "ID of the booking to complete", example = "1")
            @PathVariable Long id) {

        return bookingService.completeBooking(id);

    }
}