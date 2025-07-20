package uz.dev.rentcar.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.FavoriteDTO;
import uz.dev.rentcar.service.template.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public List<FavoriteDTO> read(@PathVariable Long userId) {
        return favoriteService.read(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public FavoriteDTO create(@RequestBody @Valid FavoriteDTO favoriteDTO) {
        return favoriteService.create(favoriteDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        favoriteService.delete(id);

        return ResponseEntity.ok("Favorite deleted successfully");
    }

}
