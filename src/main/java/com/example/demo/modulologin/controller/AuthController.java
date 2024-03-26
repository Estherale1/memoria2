package com.example.demo.modulologin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modulologin.auth.AuthResponse;
import com.example.demo.modulologin.auth.AuthService;
import com.example.demo.modulologin.auth.RolResponse;
import com.example.demo.modulologin.modelo.login.LoginAdmin;
import com.example.demo.modulologin.modelo.login.LoginRequest;
import com.example.demo.modulologin.modelo.register.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping(value = "login/admin")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginAdmin request)
    {
        return ResponseEntity.ok(authService.loginAdmin(request));
    }

    @PostMapping("/obtenerRol")
    public ResponseEntity<RolResponse> verficaRol(@RequestBody LoginRequest request) {
        
        return ResponseEntity.ok(authService.verificarRol(request));
    }
    

}

