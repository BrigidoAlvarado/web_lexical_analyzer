package org.lenguajesFP.Backend;

public class TokenError extends Token{

    public TokenError(String lexeme, String type, int row, int col) {
        super(lexeme, type, row, col);
    }
}
