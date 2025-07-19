package uz.dev.rentcar.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.service.template.UserService;

/**
 * Created by: asrorbek
 * DateTime: 7/19/25 14:30
 **/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {

        UserDTO createdDTO = userService.createUser(userDTO);

        return ResponseEntity.ok(createdDTO);

    }

}
