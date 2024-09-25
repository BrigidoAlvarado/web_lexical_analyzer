package org.lenguajesFP.Backend.enums;

public enum LexicalState {

    html(">>[html]"),
    css(">>[css]"),
    js(">>[js]");

    private final String state;

    LexicalState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
