package com.mcmanuel.LibraryManagementSystem.controller;

import com.mcmanuel.LibraryManagementSystem.pojo.LoginRequest;
import com.mcmanuel.LibraryManagementSystem.services.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class NonAuthController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<String> login(@PathVariable LoginRequest loginRequest){
        String login =userService.login(loginRequest.getEmail(),loginRequest.getPassword());
        if (login == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(login,HttpStatus.OK);
    }
}
