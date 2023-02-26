package com.vladimir.vocabularyservice.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth", url = "users:8085/auth")
//@FeignClient(name = "auth", url = "localhost:8085/auth")
public interface AuthServiceFeigenProxy {
    @GetMapping
    ResponseEntity<Void> validateUuid(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String uuid,
            @RequestHeader("username") String username
    );
}
