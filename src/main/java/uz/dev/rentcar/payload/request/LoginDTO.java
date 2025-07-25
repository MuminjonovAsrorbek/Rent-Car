package uz.dev.rentcar.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/21/25 14:08
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {

    @Schema(description = "Uniques email", defaultValue = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Username password", defaultValue = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    private String recaptchaToken;

}
