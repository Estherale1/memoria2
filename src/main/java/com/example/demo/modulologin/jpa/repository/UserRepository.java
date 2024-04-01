package com.example.demo.modulologin.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.modulologin.modelo.user.Rol;

import com.example.demo.modulologin.modelo.user.User;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username); 
    
    
    @Query("SELECT u.rol.id FROM User u WHERE u.username = :username")
Integer encontrarRolId(@Param("username") String username);




  
  
}