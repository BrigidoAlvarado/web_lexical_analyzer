package org.lenguajesFP.Backend;

public class Token {

    private String lexeme;
    private String type;
    private String regularExpression;
    private String lenguage;
    private int row;
    private int col;

    public Token(String lexeme, String type, String regularExpression, String lenguage, int row, int col) {
        this.lexeme = lexeme;
        this.type = type;
        this.regularExpression = regularExpression;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "lexema: " + lexeme + " tipo: " + type + " expReg: " + regularExpression + " fila: " + row + " col: " + col;
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
