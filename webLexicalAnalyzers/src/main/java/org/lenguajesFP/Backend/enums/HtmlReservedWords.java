package org.lenguajesFP.Backend.enums;

public enum HtmlReservedWords {

    CLASS("class"),
    HREF("href"),
    ONCLICK("onClick"),
    ID("id"),
    STYLE("style"),
    TYPE("type"),
    PLACEHOLDER("placeholder"),
    REQUIRED("required"),
    NAME("name"),
    EQUALS("=");

    private final String word;

    HtmlReservedWords(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public boolean isReservedWord(String word) {
        return this.word.equals(word);
    }
}
