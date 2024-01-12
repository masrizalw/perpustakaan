package com.perpustakaan.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perpustakaan.app.model.User;
import com.perpustakaan.app.service.UserService;
import com.perpustakaan.app.service.util.Response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @RestController 
@RequestMapping("/profil")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    public String getHello() {
        return "hello user";
    }
    
    @GetMapping("/user")
    public User getUser(String id) {
        return userService.findUserByEmailOrId(id).get();
    }
    
    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user){
        return Response.get(userService.update(user));
    }
    
}
