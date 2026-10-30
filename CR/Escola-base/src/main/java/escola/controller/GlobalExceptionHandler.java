package escola.controller;

import escola.service.ConflitoException;
import escola.service.RecursoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErrosValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erros.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<Map<String, String>> tratarConflito(ConflitoException ex) {
        Map<String, String> resposta = new HashMap<>();
        resposta.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }
}
