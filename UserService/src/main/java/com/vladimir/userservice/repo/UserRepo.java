package com.vladimir.userservice.repo;

import com.vladimir.userservice.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserName(String username);
}
