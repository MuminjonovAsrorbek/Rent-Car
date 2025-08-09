package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.ReviewDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.ReviewService;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "Review API")
@SecurityRequirement(name = "bearerAuth")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/open/{id}")
    @Operation(
            summary = "Get review by ID",
            description = "This endpoint retrieves a review by its ID. Accessible to all users."
    )
    public ReviewDTO getReview(
            @Parameter(description = "ID of the review to be retrieved", example = "1")
            @PathVariable Long id) {
        return reviewService.read(id);
    }


    @GetMapping("/open/car/{carId}")
    @Operation(
            summary = "Get all reviews for a car",
            description = "This endpoint retrieves all reviews for a specific car by its ID. Accessible to all users."
    )
    public PageableDTO getReviews(
            @Parameter(description = "ID of the car for which reviews are to be retrieved", example = "1")
            @PathVariable Long carId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return reviewService.readAll(carId, page, size);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PostMapping
    @Operation(
            summary = "Create a new review",
            description = "This endpoint allows users to create a new review. Accessible to users with ADMIN or USER roles."
    )
    public ReviewDTO createReview(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Review data to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)
                    )
            )
            @RequestBody @Valid ReviewDTO reviewDTO) {
        return reviewService.create(reviewDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing review",
            description = "This endpoint allows users to update an existing review by its ID. Accessible to users with ADMIN or USER roles."
    )
    public ReviewDTO updateReview(
            @Parameter(description = "ID of the review to be updated", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Review data to be updated",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)
                    )
            )
            @RequestBody @Valid ReviewDTO reviewDTO,
            @AuthenticationPrincipal User currentUser) {
        return reviewService.update(id, reviewDTO, currentUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a review by ID",
            description = "This endpoint allows users to delete a review by its ID. Accessible to users with ADMIN or USER roles."
    )
    public ResponseEntity<?> deleteReview(
            @Parameter(description = "ID of the review to be deleted", example = "1")
            @PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        reviewService.delete(id, currentUser);

        return ResponseEntity.ok("Review deleted successfully");
    }

}
