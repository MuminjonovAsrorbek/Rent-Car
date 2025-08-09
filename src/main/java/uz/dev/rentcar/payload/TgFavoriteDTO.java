package uz.dev.rentcar.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 8/9/25 21:24
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TgFavoriteDTO {

    private Long id;

    private boolean have;

}
