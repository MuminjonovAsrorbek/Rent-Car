package uz.dev.rentcar.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link uz.dev.rentcar.entity.Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updatedAt;

    @JsonIgnore
    private boolean deleted = false;

    @NotBlank
    private String name;

    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CarDTO> cars;
}