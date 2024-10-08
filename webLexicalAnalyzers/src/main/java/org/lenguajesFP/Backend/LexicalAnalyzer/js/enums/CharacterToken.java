package org.lenguajesFP.Backend.LexicalAnalyzer.js.enums;

public enum CharacterToken {
    Suma('+'),
    Resta('-'),
    Multiplicacion('*'),
    Division('/'),
    Menor_que('<'),
    Mayor_que('>'),
    Not('!'),
    Parentesis_apertura('('),
    Parentesis_cierre(')'),
    Corchetes_apertura('['),
    Corchetes_cierre(']'),
    Llaves_apertura('{'),
    Llaves_cierre('}'),
    Coma(','),
    Punto('.'),
    Dos_puntos(':'),
    Punto_y_coma(';'),
    Asignacion('=');

    private final char token;

    CharacterToken(char token) {
        this.token = token;
    }

    public String getEnumNameFromChar(char input) {
        for (CharacterToken ct : CharacterToken.values()) {
            if (ct.token == input) {
                return ct.name();
            }
        }
        return null;
    }

}
