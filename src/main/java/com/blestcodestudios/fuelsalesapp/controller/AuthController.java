// --- src/main/java/com/blestcodestudios/fuelsalesapp/controller/AuthController.java ---
package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.dto.LoginRequest;
import com.blestcodestudios.fuelsalesapp.dto.JwtResponse;

import com.blestcodestudios.fuelsalesapp.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest creds) {
        // 1) Delegate to Spring Security
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                creds.username(),
                creds.password()
            )
        );
        // 2) Generate JWT
        String token = jwtUtil.generateToken(auth.getName());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
