package uz.dev.rentcar.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.dev.rentcar.entity.User;

import java.util.Objects;

@Component
public class SecurityUtils {

    private final PasswordEncoder passwordEncoder;

    public SecurityUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(auth) && auth.getPrincipal() instanceof User) {

            return (User) auth.getPrincipal();

        }

        new User();
        return User.builder().email("SYSTEM@gmail.com").password(passwordEncoder.encode("SYSTEM")).build();

    }
}
