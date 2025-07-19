package uz.dev.rentcar.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 13:26
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
