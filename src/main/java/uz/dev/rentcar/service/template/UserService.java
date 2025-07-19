package uz.dev.rentcar.service.template;

import jakarta.validation.Valid;
import uz.dev.rentcar.payload.UserDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 14:30
 **/

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
}
