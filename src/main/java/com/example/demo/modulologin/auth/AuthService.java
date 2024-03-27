package com.example.demo.modulologin.auth;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.modulologin.modelo.login.LoginAdmin;
import com.example.demo.modulologin.modelo.login.LoginRequest;
import com.example.demo.modulologin.modelo.register.RegisterRequest;
import com.example.demo.modulologin.jpa.repository.UserRepository;
import com.example.demo.modulologin.modelo.user.Role;
import com.example.demo.modulologin.modelo.user.User;
import com.example.demo.modulologin.modelo.user.UserResponse;
import com.example.demo.modulologin.utils.Logs;
import com.example.demo.modulologin.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private Logs logs;
    


    public AuthResponse login(LoginRequest request) throws IOException {
        Logs logs = new Logs();
        logs.devolverLog(request.getUsername().toString(), "Login");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }

    
    public AuthResponse loginAdmin(LoginAdmin request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Verificar si el usuario tiene el rol de administrador
        if (user.getRole() != Role.ADMIN) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Mensaje de error personalizado");

        }

 

        // Generar el token
        String token = jwtService.getToken(user);

        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .country(request.getCountry())
            .role(Role.USER)
            .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }

    public RolResponse verificarRol(LoginRequest request){
        Role rol = userRepository.encontrarRol(request.getUsername()).orElseThrow();
        return RolResponse.builder().role(rol).build();
    }


    public String saludar(String username, String modulo) throws IOException{
        Logs logs = new Logs();
        logs.devolverLog(username, modulo);
        return "Hola " + username + "este es tu modulo: " + modulo;
    }

    



}
