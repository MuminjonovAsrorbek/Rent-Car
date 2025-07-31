package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.ReviewDTO;
import uz.dev.rentcar.service.template.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/open/{id}")
    public ReviewDTO getReview(@PathVariable Long id) {
        return reviewService.read(id);
    }


    @GetMapping("/open/car/{carId}")
    public List<ReviewDTO> getReviews(@PathVariable Long carId) {
        return reviewService.readAll(carId);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PostMapping
    public ReviewDTO createReview(@RequestBody @Valid ReviewDTO reviewDTO) {
        return reviewService.create(reviewDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable Long id,
                                  @RequestBody @Valid ReviewDTO reviewDTO,
                                  @AuthenticationPrincipal User currentUser) {
        return reviewService.update(id, reviewDTO, currentUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN' , 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id, @AuthenticationPrincipal User currentUser) {

        reviewService.delete(id, currentUser);

        return ResponseEntity.ok("Review deleted successfully");
    }

}
