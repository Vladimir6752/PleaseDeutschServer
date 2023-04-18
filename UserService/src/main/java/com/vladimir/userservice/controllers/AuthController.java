package com.vladimir.userservice.controllers;

import com.vladimir.userservice.models.SimpleUser;
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
    public ResponseEntity<ValidationObject<UserEntity>> login(@RequestBody SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userService.validateUserByPassword(simpleUser);

        return new ResponseEntity<>(validation, validation.getStatus());
    }

    @PostMapping("/reg")
    public ResponseEntity<ValidationObject<UserEntity>> registration(@RequestBody SimpleUser simpleUser) {
        ValidationObject<UserEntity> validation = userService.validateNewUser(simpleUser);

        return new ResponseEntity<>(validation, validation.getStatus());
    }

    @GetMapping
    public ResponseEntity<Void> validateUuid(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String uuid,
            @RequestHeader("username") String username) {
        boolean isValid = userService.validateUserByUUID(username, uuid.replace("Bearer ", ""));

        return new ResponseEntity<>(
                isValid ?
                        HttpStatus.OK
                        :
                        HttpStatus.FORBIDDEN
        );
    }
}
