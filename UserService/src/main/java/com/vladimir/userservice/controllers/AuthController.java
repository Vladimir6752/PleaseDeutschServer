package com.vladimir.userservice.controllers;

import com.vladimir.userservice.models.User;
import com.vladimir.userservice.models.UserEntity;
import com.vladimir.userservice.service.UserService;
import com.vladimir.userservice.utils.ValidationObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ValidationObject<UserEntity>> login(@RequestBody User user) {
        ValidationObject<UserEntity> validation = userService.validateUserByPassword(user);

        return new ResponseEntity<>(validation,
                validation.hasErrors()
                        ?
                        HttpStatus.FORBIDDEN
                        :
                        HttpStatus.OK
        );
    }

    @PostMapping("/reg")
    public ResponseEntity<ValidationObject<UserEntity>> registration(@RequestBody User user) {
        ValidationObject<UserEntity> validation = userService.validateNewUser(user);

        return new ResponseEntity<>(validation,
                validation.hasErrors()
                        ?
                        HttpStatus.FORBIDDEN
                        :
                        HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<Void> validateUuid(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String uuid,
            @RequestHeader("username") String username) {
        boolean isValid = userService.validateUserByUUID(username, uuid.replace("Bearer ", ""));

        return new ResponseEntity<>(
                isValid
                        ?
                        HttpStatus.OK
                        :
                        HttpStatus.FORBIDDEN
        );
    }
}
