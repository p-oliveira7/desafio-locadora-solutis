package br.com.locadora.api.exceptions;


import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> handleValidationException(ConstraintViolationException ex) {
        String errorMessage = "Erro de validação: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(errorMessage));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ResponseMessage> handleDateTimeParseException(DateTimeParseException ex) {
        String errorMessage = "Erro na conversão da data: Formato inválido.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(errorMessage));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseMessage> handleEntityNotFoundException(EntityNotFoundException ex) {
        String errorMessage = "Entidade não encontrada: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> handleInternalServerError(Exception ex) {
        String errorMessage = "Erro interno do servidor: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(errorMessage));
    }
}
