package com.nisum.apirest.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String campo) {
        super(MessageFormat.format("No se ha encontrado usuario para el valor: {0}", campo));
    }
}
