package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.mapper.UserMapper;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.UserService;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 14:31
 **/

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        User user = userMapper.toEntity(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return userMapper.toDTO(user);
    }
}
