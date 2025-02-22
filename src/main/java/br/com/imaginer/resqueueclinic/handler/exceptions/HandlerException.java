package br.com.imaginer.resqueueclinic.handler.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "localizedMessage"})
public class HandlerException extends RuntimeException {

    private final LocalDateTime localDateTime;

    public HandlerException(final String messageError) {
        super(messageError);
        this.localDateTime = LocalDateTime.now();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
