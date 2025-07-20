package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link uz.dev.rentcar.entity.Attachment}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO implements Serializable {
    private Integer id;
    private String path;
    private String originalName;
    private String contentType;
    private Long size;
    private Long carId;
}