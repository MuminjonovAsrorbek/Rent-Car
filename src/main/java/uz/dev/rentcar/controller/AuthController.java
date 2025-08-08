package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uz.dev.rentcar.payload.request.LoginDTO;
import uz.dev.rentcar.payload.request.RegisterDTO;
import uz.dev.rentcar.payload.response.TokenDTO;
import uz.dev.rentcar.service.template.AuthService;


@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Authentication API for user login, registration, and token verification")
public class AuthController {

    private final AuthService authService;

//    private final RecaptchaService recaptchaService;

    @PostMapping("/login")
    @Operation(
            summary = "Login section",
            description = "There is also a section for logging into the system and a Recaptcha section for the front end here."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwicmFuZG9tSWQiOiI0ODE5MmM4My00ZDgxLTQ1ZjAtYWZjNS03MDRmNWMzYjgzZTgiLCJleHAiOjE3NTE0ODE5MTR9.gJ9x7LdOz_uBrKVdZ2LC5xPSLZcroGtlh7tbc2vs1lQ",
                                                      "refreshToken": "ey"JhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwicmFuZG9tSWQiOiI0ODE5MmM4My00ZDgxLTQ1ZjAtYWZjNS03MDRmNWMzYjgzZTgiLCJleHAiOjE3NTE0ODE5MTR9.gJ9x7LdOz_uBrKVdZ2LC5xPSLZcroGtlh7tbc2vs1lQ"
                                                    }"""
                                    ))
                    }),
                    @ApiResponse(responseCode = "400", content = {
                            @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = "Username or password incorrect"
                            ))
                    }),
                    @ApiResponse(responseCode = "401", content = {
                            @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = "Invalid recaptcha token"
                            ))
                    })
            }
    )
    public TokenDTO login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Login information",
            content = @Content(schema = @Schema(implementation = LoginDTO.class), mediaType = "application/json"))
                          @RequestBody @Valid LoginDTO loginDTO) {

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
    @Operation(
            summary = "Register section",
            description = "This section is for registering a new user in the system."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwicmFuZG9tSWQiOiI0ODE5MmM4My00ZDgxLTQ1ZjAtYWZjNS03MDRmNWMzYjgzZTgiLCJleHAiOjE3NTE0ODE5MTR9.gJ9x7LdOz_uBrKVdZ2LC5xPSLZcroGtlh7tbc2vs1lQ",
                                                      "refreshToken": "ey"JhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwicmFuZG9tSWQiOiI0ODE5MmM4My00ZDgxLTQ1ZjAtYWZjNS03MDRmNWMzYjgzZTgiLCJleHAiOjE3NTE0ODE5MTR9.gJ9x7LdOz_uBrKVdZ2LC5xPSLZcroGtlh7tbc2vs1lQ"
                                                    }"""
                                    ))
                    }),
                    @ApiResponse(responseCode = "400", content = {
                            @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = "Email already exists"
                            ))
                    })
            }
    )
    public TokenDTO register(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Register information",
            content = @Content(schema = @Schema(implementation = RegisterDTO.class), mediaType = "application/json"))
                             @RequestBody @Valid RegisterDTO registerDTO) {

        return authService.registerUser(registerDTO);

    }

    @GetMapping("/verify")
    @Operation(
            summary = "Verify refresh token",
            description = "This endpoint is used to verify the refresh token and return a new access token."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwicmFuZG9tSWQiOiI0ODE5MmM4My00ZDgxLTQ1ZjAtYWZjNS03MDRmNWMzYjgzZTgiLCJleHAiOjE3NTE0ODE5MTR9.gJ9x7LdOz_uBrKVdZ2LC5xPSLZcroGtlh7tbc2vs1lQ",
                                                      "refreshToken": "ey"JhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJVU0VSIiwicmFuZG9tSWQiOiI0ODE5MmM4My00ZDgxLTQ1ZjAtYWZjNS03MDRmNWMzYjgzZTgiLCJleHAiOjE3NTE0ODE5MTR9.gJ9x7LdOz_uBrKVdZ2LC5xPSLZcroGtlh7tbc2vs1lQ"
                                                    }"""
                                    ))
                    }),
                    @ApiResponse(responseCode = "400", content = {
                            @Content(mediaType = "application/json", examples = @ExampleObject(
                                    value = "Invalid refresh token"
                            ))
                    })
            }
    )
    public TokenDTO verifyRefreshToken(@RequestParam String refreshToken) {

        return authService.verifyRefreshToken(refreshToken);

    }

    @GetMapping("/telegram/get-token/{phoneNumber}")
    public TokenDTO getTokenByPhoneNumber(@PathVariable String phoneNumber) {

        return authService.getTokenByPhoneNumber(phoneNumber);

    }
}
