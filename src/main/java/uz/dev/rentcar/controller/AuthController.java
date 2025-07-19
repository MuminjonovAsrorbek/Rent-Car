package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.rentcar.payload.request.LoginDTO;
import uz.dev.rentcar.payload.response.TokenDTO;
import uz.dev.rentcar.service.security.AuthService;
import uz.dev.rentcar.service.template.RecaptchaService;

/**
 * Created by: asrorbek
 * DateTime: 6/21/25 13:34
 **/

@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RecaptchaService recaptchaService;

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
}
