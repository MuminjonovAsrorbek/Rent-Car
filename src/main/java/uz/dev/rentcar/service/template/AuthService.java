package uz.dev.rentcar.service.template;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.dev.rentcar.payload.request.LoginDTO;
import uz.dev.rentcar.payload.request.RegisterDTO;
import uz.dev.rentcar.payload.response.TokenDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 13:35
 **/

public interface AuthService extends UserDetailsService {
    TokenDTO getToken(LoginDTO loginDTO);

    TokenDTO registerUser(RegisterDTO registerDTO);
}
