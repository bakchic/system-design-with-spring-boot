package com.systemdesign.controller;
import com.systemdesign.dto.LoginRequest;
import com.systemdesign.dto.LoginResponse;
import com.systemdesign.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        // DEMO authentication logic
        if (!"password".equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                request.getUsername(),
                "USER"
        );

        return new LoginResponse(token);
    }
}