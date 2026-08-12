package com.eduvault.auth;

import com.eduvault.security.CustomUserDetails;
import com.eduvault.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // O AuthenticationManager vai verificar o hash da senha com o banco
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        
        // Se chegou aqui, a senha está correta!
        String roleName = user.getUser().getRole().name();
        String token = jwtService.generateToken(user, roleName);

        return new LoginResponse(token, roleName);
    }
}
