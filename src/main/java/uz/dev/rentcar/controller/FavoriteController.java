package uz.dev.rentcar.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.FavoriteDTO;
import uz.dev.rentcar.payload.TgFavoriteDTO;
import uz.dev.rentcar.service.template.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
@Tag(name = "Favorite API", description = "Favorite API")
@SecurityRequirement(name = "bearerAuth")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get all favorites by user ID",
            description = "This endpoint retrieves all favorite items for a specific user by their user ID. Only accessible to users with the ADMIN role."
    )
    public List<FavoriteDTO> read(
            @Parameter(description = "ID of the user whose favorites are to be retrieved", example = "1")
            @PathVariable Long userId) {

        return favoriteService.read(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PostMapping
    @Operation(
            summary = "Create a new favorite",
            description = "This endpoint allows users to create a new favorite item. Accessible to users with ADMIN or USER roles."
    )
    public FavoriteDTO create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Favorite data to be created",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = FavoriteDTO.class)
                    )
            )
            @RequestBody @Valid FavoriteDTO favoriteDTO) {

        return favoriteService.create(favoriteDTO);

    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a favorite by ID",
            description = "This endpoint allows users to delete a favorite item by its ID. Accessible to users with ADMIN or USER roles."
    )
    public ResponseEntity<?> delete(
            @Parameter(description = "ID of the favorite item to be deleted", example = "1")
            @PathVariable Long id) {

        favoriteService.delete(id);

        return ResponseEntity.ok("Favorite deleted successfully");
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @Operation(
            summary = "Get my favorites",
            description = "This endpoint retrieves all favorite items for the currently authenticated user. Accessible to users with ADMIN or USER roles."
    )
    public List<FavoriteDTO> getMyFavorites(@AuthenticationPrincipal User currentUser) {

        return favoriteService.getMyFavorites(currentUser);

    }

    @GetMapping("/checked")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public TgFavoriteDTO checkFavorite(@RequestParam Long userId, @RequestParam Long carId) {

        return favoriteService.getCheck(userId, carId);

    }
}
