package org.lenguajesFP.Backend;

public class Token {

    private String lexeme;
    private String type;
    private int row;
    private int col;

    public Token(String lexeme, String type, int row, int col) {
        this.lexeme = lexeme;
        this.type = type;
        this.row = row;
        this.col = col;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
