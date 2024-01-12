package guru.springframework.spring6restmvc.controllers;

import jakarta.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.*;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler
    ResponseEntity handleError(MethodArgumentNotValidException exception){
        List errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler
    ResponseEntity handleJPAValidation(TransactionSystemException exception){
      ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
      if (exception.getCause().getCause() instanceof ConstraintViolationException){
        ConstraintViolationException ve = (ConstraintViolationException) exception.getCause().getCause();
        List errors = ve.getConstraintViolations().stream()
          .map(constraintViolation -> {
            Map<String, String> errMap = new HashMap<>();
            errMap.put(constraintViolation.getPropertyPath().toString(),
              constraintViolation.getMessage());
            return errMap;
          })
          .collect(Collectors.toList());
        return responseEntity.body(errors);
      }
      return responseEntity.build();
    }


}
