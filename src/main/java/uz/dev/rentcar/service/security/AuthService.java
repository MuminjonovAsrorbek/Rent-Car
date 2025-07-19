package uz.dev.rentcar.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.exceptions.PasswordIncorrectException;
import uz.dev.rentcar.payload.request.LoginDTO;
import uz.dev.rentcar.payload.response.TokenDTO;
import uz.dev.rentcar.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 15:14
 **/

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty())

            throw new EntityNotFoundException("User not found with username : " + username, HttpStatus.NOT_FOUND);

        return optionalUser.get();

    }

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
}
