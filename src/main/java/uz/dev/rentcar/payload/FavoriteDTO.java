package uz.dev.rentcar.payload;

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
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long id;
    private boolean deleted = false;
    private Long userId;
}