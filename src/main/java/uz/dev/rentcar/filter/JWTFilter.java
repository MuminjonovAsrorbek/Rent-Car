package uz.dev.rentcar.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.service.security.JWTService;
import uz.dev.rentcar.service.template.AuthService;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

/**
 * Created by: asrorbek
 * DateTime: 6/20/25 16:00
 **/
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    public JWTFilter(JWTService jwtService, @Lazy AuthService authService, @Lazy PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        checkBearer(request, response);

        checkBasic(response, authorization);

        checkParamAuth(request, response);

        filterChain.doFilter(request, response);
    }

    private void checkParamAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String auth = request.getParameter("auth");

        if (Objects.isNull(auth) || !auth.startsWith("Basic ")) {
            return;
        }

        if (auth.startsWith("Bearer ")) {
            checkBearer(request, response);
        }

        if (auth.startsWith("Basic ")) {
            checkBasic(response, auth);
        }

    }

    private void checkBasic(HttpServletResponse response, String authorization) throws IOException {

        String prefix = "Basic ";

        if (Objects.isNull(authorization) || !authorization.startsWith(prefix)) {

            return;
        }

        String token = authorization.substring(prefix.length());

        String encoded = new String(Base64.getDecoder().decode(token));

        String[] parts = encoded.split(":");

        if (parts.length != 2) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Basic authentication format");

            return;
        }

        String username = parts[0];
        String rawPassport = parts[1];

        User user = (User) authService.loadUserByUsername(username);

        String encodedPassword = user.getPassword();

        if (!passwordEncoder.matches(rawPassport, encodedPassword)) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");

            return;
        }

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        ));

    }

    private void checkBearer(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorization = request.getHeader("Authorization");

        if (Objects.isNull(authorization) || !authorization.startsWith("Bearer ")) {

            return;

        }

        String token = authorization.substring(7);

        try {

            String email = jwtService.parseToken(token);

            User user = (User) authService.loadUserByUsername(email);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
            ));
        } catch (io.jsonwebtoken.ExpiredJwtException e) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");

        } catch (Exception e) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }
}
