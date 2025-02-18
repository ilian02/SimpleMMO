package com.example.Profiles.controller;

import com.example.Profiles.Dto.Response;
import com.example.Profiles.Dto.UserLoginDto;
import com.example.Profiles.Dto.UserRegisterDto;
import com.example.Profiles.service.MMOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
public class MMOUserController {

    @Autowired
    private MMOUserService userService;

    @PostMapping("/api/auth/register")
    public ResponseEntity<Response> register(@RequestBody UserRegisterDto userRegisterDto) {
        Response resp = userService.register(userRegisterDto);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<Response> login(@RequestBody UserLoginDto userLoginDto) {
        Response resp = userService.login(userLoginDto);
        return ResponseEntity.status(resp.getStatusCode()).body(resp);
    }
}
