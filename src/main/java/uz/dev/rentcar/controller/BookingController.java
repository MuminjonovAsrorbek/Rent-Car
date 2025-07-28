package uz.dev.rentcar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.BookingCreateDTO;
import uz.dev.rentcar.payload.BookingDTO;
import uz.dev.rentcar.service.template.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public BookingDTO createBooking(@RequestBody BookingCreateDTO dto, @AuthenticationPrincipal User currentUser) {

        return bookingService.createBooking(dto, currentUser);

    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public List<BookingDTO> getMyBookings(@AuthenticationPrincipal User currentUser) {

        return bookingService.getMyBookings(currentUser);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bookingService.getBookingById(id, currentUser));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        BookingDTO bookingDTO = bookingService.cancelBooking(id, currentUser);

        return ResponseEntity.ok(bookingDTO);
    }

    // ====== ADMIN ENDPOINTS ======

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingDTO>> getBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
    }

    @PostMapping("/{id}/confirm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDTO> confirmBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.confirmBooking(id));
    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingDTO> completeBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.completeBooking(id));
    }
}