package org.lenguajesFP.Backend.exceptions;

import org.lenguajesFP.Backend.TokenError;

public class InvalidTokenException extends Exception{

    private TokenError error;

    public InvalidTokenException(TokenError error) {
        this.error = error;
    }

    public TokenError getError() {
        return error;
    }
}
