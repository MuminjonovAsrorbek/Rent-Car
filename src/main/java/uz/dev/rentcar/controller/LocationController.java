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
@Tag(name = "Location API", description = "Location API")
@SecurityRequirement(name = "bearerAuth")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('GPS', 'ADMIN')")
    @Operation(
            summary = "Create a new car location",
            description = "This endpoint allows you to create a new car location. Only users with 'GPS' or 'ADMIN' roles can access this endpoint."
    )
    public CarLocationDTO createLocation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Car location data",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CarLocationDTO.class)
                    )
            )
            @RequestBody @Valid CarLocationDTO carLocationDTO) {

        return locationService.createLocation(carLocationDTO);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GPS', 'ADMIN')")
    @Operation(
            summary = "Update an existing car location",
            description = "This endpoint allows you to update an existing car location by its ID. Only users with 'GPS' or 'ADMIN' roles can access this endpoint."
    )
    public CarLocationDTO updateLocation(
            @Parameter(
                    description = "ID of the car location to update",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated car location data",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = CarLocationDTO.class)
                    )
            )
            @RequestBody @Valid CarLocationDTO carLocationDTO) {

        return locationService.updateLocation(id, carLocationDTO);

    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(
            summary = "Get booking locations",
            description = "This endpoint retrieves the locations associated with a specific booking ID. Accessible to users with 'USER' or 'ADMIN' roles."
    )
    public List<CarLocationDTO> getBookingLocations(
            @Parameter(
                    description = "ID of the booking to retrieve locations for",
                    required = true,
                    example = "1"
            )
            @PathVariable Long bookingId,
            @AuthenticationPrincipal User currentUser) {

        return locationService.getBookingLocations(bookingId, currentUser);

    }
}
