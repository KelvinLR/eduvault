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

    // construtor recebe o authmanager e jwt padrao do spring
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // auth manager vai ver se o hash bate com oq tem no banco
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        
        // se veio pra ca eh pq ta certo
        String roleName = user.getUser().getRole().name();
        String token = jwtService.generateToken(user, roleName);

        // retorna a response do login com token e cargo apenas
        return new LoginResponse(token, roleName);
    }
}
