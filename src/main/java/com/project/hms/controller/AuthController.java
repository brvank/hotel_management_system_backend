package com.project.hms.controller;

import com.project.hms.service.AuthService;
import com.project.hms.utility.AppResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    AppResponse appResponse;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> requestMap){
        return authService.login(requestMap);
    }

}