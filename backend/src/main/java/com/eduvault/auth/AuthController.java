package com.eduvault.auth;

import org.springframework.web.bind.annotation.*;

@RestController // isso vai retornar automaticamente um JSON ou XML
@RequestMapping("/auth")
// mapeia uma requisicao http p controller e metodos
// ela eh a mãe e meio q define a URL base do nosso request rsrs
public class AuthController {
    // essa annotation vai enviar coisa p serv (eh so um POST)
    @PostMapping("/login")
    // o request body vai trazer uma requisicao http p um objeto jaaj
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return new LoginResponse(
                "token", "ADM-mock"
        );
    }
}
