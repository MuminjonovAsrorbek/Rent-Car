package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 8/8/25 17:56
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TgUserDTO {

    private Long id;

    private Boolean active;

}
