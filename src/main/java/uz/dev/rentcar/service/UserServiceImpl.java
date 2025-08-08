package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.exceptions.EntityAlreadyExistException;
import uz.dev.rentcar.mapper.UserMapper;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.UserService;
import uz.dev.rentcar.utils.SecurityUtils;

import java.util.List;
import java.util.Optional;

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

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())) {

            throw new EntityAlreadyExistException("User already exist by email : " + userDTO.getEmail(), HttpStatus.CONFLICT);

        }

        User user = userMapper.toEntity(userDTO);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

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

        if (userRepository.existsByEmail(userDTO.getEmail()) && !user.getEmail().equals(userDTO.getEmail())) {

            throw new EntityAlreadyExistException(userDTO.getEmail(), HttpStatus.CONFLICT);

        }

        user.setEmail(userDTO.getEmail());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        userRepository.save(user);

        return userMapper.toDTO(user);

    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findByIdOrThrowException(id);

        userRepository.delete(user);

    }

    @Override
    public UserDTO getUserInfo() {

        User currentUser = securityUtils.getCurrentUser();

        return userMapper.toDTO(currentUser);
    }

    @Override
    public Boolean getUserByPhoneNumber(String phoneNumber) {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        return optionalUser.isPresent();
    }
}
