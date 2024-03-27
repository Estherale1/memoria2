package com.example.demo.modulologin.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modulologin.auth.AuthResponse;
import com.example.demo.modulologin.auth.AuthService;
import com.example.demo.modulologin.auth.RolResponse;
import com.example.demo.modulologin.modelo.login.LoginAdmin;
import com.example.demo.modulologin.modelo.login.LoginRequest;
import com.example.demo.modulologin.modelo.modeloodoo.Producto;
import com.example.demo.modulologin.modelo.modeloodoo.Stock;
import com.example.demo.modulologin.modelo.register.RegisterRequest;
import com.example.demo.modulologin.servicio.Servicio;
import com.example.demo.modulologin.utils.Logs;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final Servicio service;
    private Logs logs;
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws IOException
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
    

    @PostMapping("/prueba")
    public String postMethodName(@RequestBody String username, String modulo) throws IOException {

        return authService.saludar(username, modulo);
    }
    

    @PostMapping("/validarFactura")
    public String postMethodNae(@RequestBody String username, String modulo) throws IOException {

        return authService.saludar(username, modulo);
    }

    @PostMapping("/datos")
    public List<Producto> devolverStock(@RequestBody String username, String modulo) throws XmlRpcException, IOException {
        System.out.println(username + modulo);
        List<Producto> listaProducto = service.devolverProductos();
        List<Stock> listaStock = service.devolverStock();
        return service.actualizarProductosConStock(listaProducto, listaStock, username, modulo);
    }

    @PostMapping("/datos/{id}")
    public Producto devolverUnProducto(@PathVariable int id, @RequestBody String username, String modulo)
            throws XmlRpcException, IOException {
        return service.buscarProducto(id, username, modulo);
    }

    @PatchMapping("/actualizar/{id}")
    public void actualizarStock(@PathVariable int id, @RequestBody Map<String, Double> requestBody)
            throws XmlRpcException, MalformedURLException {
        double quantity = requestBody.get("quantity");
        service.actualizarStock(id, quantity);
    }
}

