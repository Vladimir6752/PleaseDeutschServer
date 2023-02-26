package com.vladimir.userservice.service;

import com.vladimir.userservice.models.User;
import com.vladimir.userservice.models.UserEntity;
import com.vladimir.userservice.repo.UserRepo;
import com.vladimir.userservice.utils.UserValidator;
import com.vladimir.userservice.utils.ValidationObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private UserValidator userValidator;

    public UserEntity saveNewUser(User user) {
        return userRepo.save(new UserEntity(user));
    }



    public ValidationObject<UserEntity> validateUserByPassword(User user) {
        ValidationObject<UserEntity> validation = userValidator.validateUserByPassword(user);

        if(validation.noErrors()) {
            validation.setValue(userRepo.findByUserName(user.getUsername()));
        }

        return validation;
    }

    public ValidationObject<UserEntity> validateNewUser(User user) {
        ValidationObject<UserEntity> validation = userValidator.validateNewUser(user);

        if(validation.noErrors()) {
            validation.setValue(saveNewUser(user));
        }

        return validation;
    }

    public boolean validateUserByUUID(String username, String uuid) {
        return userValidator.validateUserByUUID(username, uuid);
    }
}
