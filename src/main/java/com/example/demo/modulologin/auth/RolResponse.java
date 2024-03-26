package com.example.demo.modulologin.auth;



import com.example.demo.modulologin.modelo.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolResponse {
    Role role;
}
