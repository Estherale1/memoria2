package com.example.demo.modulologin.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.modulologin.modelo.user.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
   
}
