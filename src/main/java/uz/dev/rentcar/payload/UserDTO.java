package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.rentcar.enums.RoleEnum;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link uz.dev.rentcar.entity.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonIgnore
    private boolean deleted = false;

    @NotBlank
    @Schema(
            description = "Full name of the user",
            example = "John Doe"
    )
    private String fullName;

    @Email
    @NotBlank
    @Schema(
            description = "Email address of the user",
            example = "jonh@gmail.com")
    @Pattern(regexp = ".*@gmail\\.com$", message = "Only @gmail.com addresses are allowed")
    private String email;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(
            description = "Password of the user",
            example = "11111111",
            accessMode = Schema.AccessMode.WRITE_ONLY
    )
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\+998\\d{9}$", message = "Phone number must start with +998 and contain 13 digits")
    @Schema(description = "Phone number of the user", example = "+998901234567")
    @Size(min = 13, max = 13, message = "Phone number must be exactly 13 characters long")
    private String phoneNumber;

    @NotNull
    @Schema(
            description = "Role of the user",
            example = "USER",
            allowableValues = "USER, ADMIN, GPS"
    )
    private RoleEnum role;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<BookingDTO> bookings;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ReviewDTO> reviews;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<FavoriteDTO> favorites;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<NotificationDTO> notifications;
}