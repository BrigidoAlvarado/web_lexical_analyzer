package org.lenguajesFP.Backend.LexicalAnalyzer.js.enums;

public enum TwoCharacterToken {
    Igual("=="),
    Menor_o_igual_que("<="),
    Mayor_o_igual_que(">="),
    Diferente("!="),
    Or("||"),
    And("&&"),
    Not("!"),
    Incremento("++"),
    Decremento("--");

    private final String token;

    TwoCharacterToken(String token) {
        this.token = token;
    }

    public String getEnumNameFromChar(String input) {
        for (TwoCharacterToken tct : TwoCharacterToken.values()) {
            if (tct.token.equals(input)) {
                return tct.name();
            }
        }
        return null;
    }
}
