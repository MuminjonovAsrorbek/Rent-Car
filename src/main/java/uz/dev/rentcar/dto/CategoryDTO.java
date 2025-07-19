package uz.dev.rentcar.dto;

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

    private Long id;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private boolean deleted = false;

    private String name;

    private String description;

    private List<CarDTO> cars;
}