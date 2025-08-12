package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.PenaltyDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.PenaltyService;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 14:08
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/penalties")
@Tag(name = "Penalty API", description = "Penalty API")
@SecurityRequirement(name = "bearerAuth")
public class PenaltyController {

    private final PenaltyService penaltyService;

    @GetMapping("/me/all-penalties")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Get all penalties for the authenticated user",
            description = "This endpoint retrieves all penalties associated with the currently authenticated user."
    )
    public PageableDTO getMyPenalties(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                      @AuthenticationPrincipal User currentUser) {

        return penaltyService.getMyPenalties(currentUser, page, size);

    }

    @GetMapping("/me/overdue-returns")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Get overdue returns for the authenticated user",
            description = "This endpoint retrieves all overdue returns associated with the currently authenticated user."
    )
    public PageableDTO getMyOverdueReturns(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @AuthenticationPrincipal User currentUser) {

        return penaltyService.getMyOverdueReturns(currentUser, page, size);

    }

    @PatchMapping("/confirm/{bookingId}/booking")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(
            summary = "Confirm penalty for a booking",
            description = "This endpoint confirms a penalty for a specific booking by its ID."
    )
    public PenaltyDTO confirmPenalty(
            @Parameter(description = "ID of the booking to confirm penalty for", example = "12345")
            @PathVariable Long bookingId) {

        return penaltyService.confirmPenalty(bookingId);

    }

    @PatchMapping("/confirm/{penaltyId}/penalty")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(
            summary = "Confirm penalty by penalty ID",
            description = "This endpoint confirms a penalty using its unique ID."
    )
    public PenaltyDTO confirmPenaltyWithPenaltyId(
            @Parameter(description = "ID of the penalty to confirm", example = "6")
            @PathVariable Long penaltyId) {

        return penaltyService.confirmPenaltyWithPenaltyId(penaltyId);

    }

    @PatchMapping("/cancel/{bookingId}/booking")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(
            summary = "Cancel penalty for a booking",
            description = "This endpoint cancels a penalty associated with a specific booking by its ID."
    )
    public PenaltyDTO cancelPenaltyWithBookingId(
            @Parameter(description = "ID of the booking to cancel penalty for", example = "12345")
            @PathVariable Long bookingId) {

        return penaltyService.cancelPenaltyWithBookingId(bookingId);

    }

    @PatchMapping("/cancel/{penaltyId}/penalty")
    @PreAuthorize("hasAnyRole('ADMIN' )")
    @Operation(
            summary = "Cancel penalty by penalty ID",
            description = "This endpoint cancels a penalty using its unique ID."
    )
    public PenaltyDTO cancelPenaltyWithPenaltyId(
            @Parameter(description = "ID of the penalty to cancel", example = "6")
            @PathVariable Long penaltyId) {

        return penaltyService.cancelPenaltyWithPenaltyId(penaltyId);

    }
}
