package br.com.imaginer.resqueueclinic.handler;

import br.com.imaginer.resqueueclinic.handler.exceptions.HandlerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(HandlerException.class)
    public ResponseEntity<HandlerException> handlerException(HandlerException e) {
        return ResponseEntity.badRequest().body(e);
    }

}
