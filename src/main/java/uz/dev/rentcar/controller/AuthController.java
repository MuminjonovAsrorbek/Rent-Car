package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.request.LoginDTO;
import uz.dev.rentcar.payload.request.RegisterDTO;
import uz.dev.rentcar.payload.response.TokenDTO;
import uz.dev.rentcar.service.template.AuthService;
import uz.dev.rentcar.service.template.RecaptchaService;


@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    private final RecaptchaService recaptchaService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody @Valid LoginDTO loginDTO) {

//        boolean isValid = recaptchaService.verify(loginDTO.getRecaptchaToken());
//
//        if (!isValid) {
//
//            throw new InvalidRecaptchaTokenException("Invalid recaptcha token", HttpStatus.UNAUTHORIZED);
//
//        }


        return authService.getToken(loginDTO);

    }

    @PostMapping("/register")
    public TokenDTO register(@RequestBody @Valid RegisterDTO registerDTO) {

        return authService.registerUser(registerDTO);

    }

    @GetMapping("/verify")
    public TokenDTO verifyRefreshToken(@RequestParam String refreshToken) {

        return authService.verifyRefreshToken(refreshToken);

    }
}
