package com.mcmanuel.controller;

import com.mcmanuel.DTO.UserDTO;
import com.mcmanuel.entities.User;
import com.mcmanuel.pojo.LoginRequest;
import com.mcmanuel.pojo.UserRequest;
import com.mcmanuel.services.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequest request){
        return new ResponseEntity<>(userService.createUser(request),HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId){
        UserDTO dto =userService.getUser(userId);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<Page<UserDTO>> getAllUser(
            @RequestParam(required = false,defaultValue = "0") int pageNo, @RequestParam(required = false,defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getAllUser(pageNo,size));
    }

    @PutMapping("/{userId}")
    ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody User updatedUser){
        UserDTO savedUser = userService.updateUser(userId,updatedUser);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable UUID userId){
        String savedUser = userService.deleteUser(userId);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String login =userService.login(loginRequest.getEmail(),loginRequest.getPassword());
        if (login == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(login,HttpStatus.OK);
    }

}
