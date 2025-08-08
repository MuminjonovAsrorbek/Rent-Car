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
@Tag(name = "User API", description = "User API")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new user",
            description = "This endpoint allows an admin to create a new user. The user details must be provided in the request body."
    )
    public ResponseEntity<UserDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data to be created",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            )
            @RequestBody @Valid UserDTO userDTO) {

        UserDTO createdDTO = userService.createUser(userDTO);

        return ResponseEntity.ok(createdDTO);

    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all users",
            description = "This endpoint retrieves a paginated list of all users. Only accessible to users with the ADMIN role."
    )
    public PageableDTO getAllUsers(@Parameter(description = "Page number", example = "0")
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @Parameter(description = "Page size", example = "10")
                                   @RequestParam(value = "size", defaultValue = "10") int size) {

        return userService.getAllUsers(page, size);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get user by ID",
            description = "This endpoint retrieves a user by their ID. Only accessible to users with the ADMIN role."
    )
    public UserDTO getUserById(
            @Parameter(description = "ID of the user to be retrieved", example = "1")
            @PathVariable Long id) {

        return userService.getUserById(id);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update user by ID",
            description = "This endpoint allows an admin to update a user's details by their ID. The updated user details must be provided in the request body."
    )
    public UserDTO updateUser(
            @Parameter(description = "ID of the user to be updated", example = "1")
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            )
            @RequestBody @Valid UserDTO userDTO) {

        return userService.updateUser(id, userDTO);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete user by ID",
            description = "This endpoint allows an admin to delete a user by their ID. The user will be permanently removed from the system."
    )
    public ResponseEntity<?> deleteUser(
            @Parameter(description = "ID of the user to be deleted", example = "1")
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/me")
    @Operation(
            summary = "Get current user info",
            description = "This endpoint retrieves the information of the currently authenticated user. No parameters are required."
    )
    public UserDTO getUserInfo() {

        return userService.getUserInfo();

    }

    @GetMapping("/open/telegram/get-by-phoneNumber/{phoneNumber}")
    public Boolean getByPhoneNumber(@PathVariable String phoneNumber) {

        return userService.getUserByPhoneNumber(phoneNumber);

    }
}
