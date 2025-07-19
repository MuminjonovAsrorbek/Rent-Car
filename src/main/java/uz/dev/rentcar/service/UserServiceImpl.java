package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.mapper.UserMapper;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.UserService;

import java.util.List;

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

    @Override
    public PageableDTO getAllUsers(int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage = userRepository.findAll(pageable);

        List<User> users = userPage.getContent();

        return new PageableDTO(
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.hasNext(),
                userPage.hasPrevious(),
                userMapper.toDTO(users)
        );
    }

    @Override
    public UserDTO getUserById(Long id) {

        User user = userRepository.findByIdOrThrowException(id);

        return userMapper.toDTO(user);

    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {

        User user = userRepository.findByIdOrThrowException(id);

        // update qilis kerak user uchun

        return new UserDTO();
    }

    @Override
    public void deleteUser(Long id) {

    }
}
