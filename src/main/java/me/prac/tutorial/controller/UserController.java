package me.prac.tutorial.controller;

import me.prac.tutorial.dto.UserDto;
import me.prac.tutorial.entity.User;
import me.prac.tutorial.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.signup(userDto));
    }
    //UserDto 를 파라미터로 받아서 UserService 의 signup 메소드 호출 .

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {

        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {

        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
