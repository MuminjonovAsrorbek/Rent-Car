package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.TgUserDTO;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 14:30
 **/

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    PageableDTO getAllUsers(int page, int size);

    UserDTO getUserById(Long id);

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO getUserInfo();

    TgUserDTO getUserByPhoneNumber(String phoneNumber);
}
