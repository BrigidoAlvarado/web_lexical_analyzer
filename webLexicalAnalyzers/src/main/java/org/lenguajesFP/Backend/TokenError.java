package org.lenguajesFP.Backend;

import org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer.Other;
import org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer.Rule;
import org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer.TagOrType;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers.ReservedWordsAnalyzer;
import org.lenguajesFP.Backend.enums.HtmlReservedWords;
import org.lenguajesFP.Backend.enums.HtmlTag;

public class TokenError extends Token{

    private String suggestedLanguage;

    public TokenError(String lexeme, String type, String language, int row, int col) {
        super(lexeme, type, null, language, row, col);
        if (lexeme != null){
            setSuggestedLanguage(lexeme);
        }
    }

    public String getSuggestedLanguage() {
        return suggestedLanguage;
    }

    private void setSuggestedLanguage(String lexeme) {
        if (isHtmlReservedWord(lexeme)){
            suggestedLanguage = "HTML";
        } else if (isCssReservedWord(lexeme)){
            suggestedLanguage = "CSS";
        } else if (isJsReservedWord(lexeme)) {
            suggestedLanguage = "Javascript";
        } else {
            suggestedLanguage = "";
        }
    }

    private boolean isHtmlReservedWord(String lexeme) {
        try {
            HtmlReservedWords.valueOf(lexeme);
            return true;
        }catch (IllegalArgumentException e) {
            try {
                HtmlTag.valueOf(lexeme);
                return true;
            }catch (IllegalArgumentException e2) {
                return false;
            }
        }
    }

    private boolean isCssReservedWord(String lexeme) {
        return (Other.EXCEPTION_TOKENS.contains(lexeme) ||
                Other.OTHERS.contains(lexeme) ||
                Other.ESPECIAL_TOKEN.equalsIgnoreCase(lexeme) ||
                Rule.COMBINED_RULES.contains(lexeme) ||
                Rule.RULES.contains(lexeme) ||
                TagOrType.TAG_OR_TYPE.contains(lexeme));
    }

    private boolean isJsReservedWord(String lexeme) {
        return (ReservedWordsAnalyzer.BOOLEAN_RESERVED_WORDS.contains(lexeme) ||
                ReservedWordsAnalyzer.RESERVED_WORDS.contains(lexeme));
    }
}
