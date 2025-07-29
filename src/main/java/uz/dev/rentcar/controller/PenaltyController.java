package uz.dev.rentcar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.PenaltyDTO;
import uz.dev.rentcar.service.template.PenaltyService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/29/25 14:08
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/penalties")
public class PenaltyController {

    private PenaltyService penaltyService;

    @GetMapping("/me/all-penalties")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public List<PenaltyDTO> getMyPenalties(@AuthenticationPrincipal User currentUser) {

        return penaltyService.getMyPenalties(currentUser);

    }

    @GetMapping("/me/overdue-returns")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    public List<PenaltyDTO> getMyOverdueReturns(@AuthenticationPrincipal User currentUser) {

        return penaltyService.getMyOverdueReturns(currentUser);

    }

    @PatchMapping("/confirm/{bookingId}/booking")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PenaltyDTO confirmPenalty(@PathVariable Long bookingId) {

        return penaltyService.confirmPenalty(bookingId);

    }

    @PatchMapping("/confirm/{penaltyId}/penalty")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PenaltyDTO confirmPenaltyWithPenaltyId(@PathVariable Long penaltyId) {

        return penaltyService.confirmPenaltyWithPenaltyId(penaltyId);

    }

    @PatchMapping("/cancel/{bookingId}/booking")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PenaltyDTO cancelPenaltyWithBookingId(@PathVariable Long bookingId) {

        return penaltyService.cancelPenaltyWithBookingId(bookingId);

    }

    @PatchMapping("/cancel/{penaltyId}/penalty")
    @PreAuthorize("hasAnyRole('ADMIN' )")
    public PenaltyDTO cancelPenaltyWithPenaltyId(@PathVariable Long penaltyId) {

        return penaltyService.cancelPenaltyWithPenaltyId(penaltyId);

    }
}
