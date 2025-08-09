package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.Favorite}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    @NotNull
    @Schema(
            description = "ID of the user who added the favorite",
            example = "1"
    )
    private Long userId;

    @NotNull
    @Schema(
            description = "ID of the car that is marked as favorite",
            example = "1"
    )
    private Long carId;
}