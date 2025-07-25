package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.CarLocationDTO;
import uz.dev.rentcar.service.template.LocationService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/25/25 23:08
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('GPS', 'ADMIN')")
    public CarLocationDTO createLocation(@RequestBody @Valid CarLocationDTO carLocationDTO) {

        return locationService.createLocation(carLocationDTO);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GPS', 'ADMIN')")
    public CarLocationDTO updateLocation(@PathVariable Long id, @RequestBody @Valid CarLocationDTO carLocationDTO) {

        return locationService.updateLocation(id, carLocationDTO);

    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<CarLocationDTO> getBookingLocations(@PathVariable Long bookingId,
                                                    @AuthenticationPrincipal User currentUser) {

        return locationService.getBookingLocations(bookingId, currentUser);

    }
}
