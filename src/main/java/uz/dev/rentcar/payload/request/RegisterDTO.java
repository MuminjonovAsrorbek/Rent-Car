package uz.dev.rentcar.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 13:26
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO implements Serializable {

    @NotBlank
    @Schema(description = "Full name of the user", example = "John Doe")
    @Size(min = 3, message = "Full name must be at least 3 characters long")
    private String fullName;

    @Email
    @NotBlank
    @Pattern(regexp = ".*@gmail\\.com$", message = "Only @gmail.com addresses are allowed")
    @Schema(description = "Email address of the user", example = "user@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Username of the user", example = "user1")
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must start with +998 and contain 13 digits")
    @Schema(description = "Phone number of the user", example = "+998901234567")
    @Size(min = 13, max = 13, message = "Phone number must be exactly 13 characters long")
    private String phoneNumber;
}
