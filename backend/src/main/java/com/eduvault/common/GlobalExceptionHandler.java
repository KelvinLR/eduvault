package com.eduvault.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// @ControllerAdvice escuta exceções em TODOS os Controllers da aplicação
@ControllerAdvice
public class GlobalExceptionHandler {

    // Sempre que uma RuntimeException genérica estourar, esse método será chamado
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // Você pode adicionar quantos @ExceptionHandler quiser para diferentes tipos de exceções!
    // Por exemplo, quando formos fazer o Login, criaremos um para "BadCredentialsException" 
    // retornando um status 401 Unauthorized.
}
