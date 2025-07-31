package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link uz.dev.rentcar.entity.Review}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    @JsonIgnore
    private boolean deleted = false;

    @NotNull
    @Schema(
            description = "Rating given by the user",
            example = "5"
    )
    @Size(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int rating;

    @NotBlank
    @Schema(
            description = "Comment provided by the user",
            example = "Great car and service!"
    )
    private String comment;

    @NotNull
    @Schema(
            description = "ID of the user who made the review",
            example = "1"
    )
    private Long userId;

    @Schema(
            description = "ID of the car being reviewed",
            example = "1"
    )
    private Long carId;
}