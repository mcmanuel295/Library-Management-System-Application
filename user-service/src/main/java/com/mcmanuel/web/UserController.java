package com.mcmanuel.web;

import com.mcmanuel.domain.user.UserDTO;
import com.mcmanuel.entities.User;
import com.mcmanuel.domain.user.LoginRequest;
import com.mcmanuel.domain.user.Role;
import com.mcmanuel.domain.user.UserRequest;
import com.mcmanuel.services.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId){
        UserDTO dto =userService.getUser(userId);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    ResponseEntity<UserDTO> updateUserRole(@PathVariable UUID userId, @RequestBody Role role){
        UserDTO savedUser = userService.updateRole(userId,role);
        if (savedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedUser,HttpStatus.OK);
    }

    @PostMapping("/{email}/{token}")
    ResponseEntity<String> activateUser(@PathVariable String email,@PathVariable String token){
        if (!userService.activateAccount(email,token)){
            return new ResponseEntity<>("Error", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok("Account activated");
    }

}
