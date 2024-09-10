package org.somemate.demo.user.controller;

import org.somemate.demo.user.dto.User;
import org.somemate.demo.user.service.UserService;
import org.somemate.demo.util.JWTUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    UserService userService;
    JWTUtil jwtUtil;


    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;//생성자 주입
        this.jwtUtil = jwtUtil;            //생성자 주입
    }

    @GetMapping
    @RequestMapping("/test")
    public User test() {
        User user;

        try {
            user = userService.getTestUser();
            System.out.println("user service : " + user);
        }
        catch (SQLException e) {e.printStackTrace(); user = null;}
    return user;
    }
}
