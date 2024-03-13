package com.example.demo;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Dto.UserDto;
import com.example.demo.Service.UserService;

@RestController
public class RestUserController {

    private UserDetailsService userDetailsService = null;
    private UserService userService = null;

    @Autowired
    public void UserController(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/api/home")
    public UserDetails home(Principal principal) {
        return userDetailsService.loadUserByUsername(principal.getName());
    }

    @GetMapping("/api/login")
    public String login() {
        return "login";
    }
    @GetMapping("/api/register")
    public String register() {
        return "register";
    }

    @PostMapping("/api/register")
    public String registerSave(@RequestBody UserDto userDto) {
        userService.save(userDto);
        return "Registration Successful . You can login now";
    }
}
