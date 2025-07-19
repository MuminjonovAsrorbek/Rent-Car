package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.service.template.UserService;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 14:30
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {

        UserDTO createdDTO = userService.createUser(userDTO);

        return ResponseEntity.ok(createdDTO);

    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public PageableDTO getAllUsers(@Parameter(description = "Page number", example = "0")
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @Parameter(description = "Page size", example = "10")
                                   @RequestParam(value = "size", defaultValue = "10") int size) {

        return userService.getAllUsers(page, size);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO getUserById(@PathVariable Long id) {

        return userService.getUserById(id);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {

        return userService.updateUser(id, userDTO);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }

}
