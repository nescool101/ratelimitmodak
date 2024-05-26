package com.co.modak.ratelimiter.modakratelimiter.exception;

import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceBadRequestException;
import com.co.modak.ratelimiter.modakratelimiter.exception.impl.ResourceNotFoundException;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageDTO;
import com.co.modak.ratelimiter.modakratelimiter.model.generalMessage.MessageResourceNotFoundDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<MessageDTO> handleException() {

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        messageDTO.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageDTO);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<MessageResourceNotFoundDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {

        MessageResourceNotFoundDTO messageResourceNotFoundDTO = new MessageResourceNotFoundDTO();
        messageResourceNotFoundDTO.setCode(HttpStatus.NOT_FOUND.value());
        messageResourceNotFoundDTO.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResourceNotFoundDTO);
    }

    @ExceptionHandler(ResourceBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<MessageDTO> handleConversion(RuntimeException ex) {

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setCode(HttpStatus.BAD_REQUEST.value());
        messageDTO.setMessage(ex.getMessage());

        return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
    }
}
