package org.lenguajesFP.Backend;

public class TokenError extends Token{

    public TokenError(String lexeme, String type, String lenguage, int row, int col) {
        super(lexeme, type, null, lenguage, row, col);
    }
}
