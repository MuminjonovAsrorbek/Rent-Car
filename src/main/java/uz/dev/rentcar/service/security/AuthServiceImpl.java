package uz.dev.rentcar.service.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.exceptions.EntityAlreadyExistException;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.exceptions.PasswordIncorrectException;
import uz.dev.rentcar.mapper.UserMapper;
import uz.dev.rentcar.payload.request.LoginDTO;
import uz.dev.rentcar.payload.request.RegisterDTO;
import uz.dev.rentcar.payload.response.TokenDTO;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.AuthService;

import java.util.Date;
import java.util.Optional;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 15:14
 **/

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty())

            throw new EntityNotFoundException("User not found with email : " + email, HttpStatus.NOT_FOUND);

        return optionalUser.get();

    }

    @Override
    public TokenDTO getToken(LoginDTO loginDTO) {

        User user = loadUserByUsername(loginDTO.getUsername());

        String encodedPassword = user.getPassword();

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), encodedPassword);

        if (!matches) {

            throw new PasswordIncorrectException("Password incorrect", HttpStatus.BAD_REQUEST);

        }

        String accessToken = jwtService.generateToken(loginDTO.getUsername(), new Date(System.currentTimeMillis() + 3 * 3600 * 1000));

        String refreshToken = jwtService.generateToken(loginDTO.getUsername(), new Date(System.currentTimeMillis() + 12 * 3600 * 1000));

        return new TokenDTO(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public TokenDTO registerUser(RegisterDTO registerDTO) {

        boolean exists = userRepository.existsByEmail(registerDTO.getEmail());

        if (exists)
            throw new EntityAlreadyExistException("User already registered by email :" + registerDTO.getEmail(), HttpStatus.BAD_REQUEST);

        User user = userMapper.toEntity(registerDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        LoginDTO loginDTO = new LoginDTO(user.getUsername(), user.getPassword());

        return getToken(loginDTO);
    }
}
