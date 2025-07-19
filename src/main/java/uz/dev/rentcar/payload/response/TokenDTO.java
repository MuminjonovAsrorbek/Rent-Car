package uz.dev.rentcar.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 15:44
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

}
