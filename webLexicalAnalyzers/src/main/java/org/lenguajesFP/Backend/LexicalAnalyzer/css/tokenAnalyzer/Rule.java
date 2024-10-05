package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

import java.util.Arrays;
import java.util.List;

public class Rule extends LexicalAnalyzer {

    public static final List<String> RULES = Arrays.asList(
            "color",
            "background",
            "width",
            "height",
            "display",
            "inline",
            "block",
            "flex",
            "grid",
            "none",
            "margin",
            "border",
            "padding",
            "content",
            "top",
            "bottom",
            "left",
            "right",
            "auto",
            "float",
            "position",
            "static",
            "relative",
            "absolute",
            "sticky",
            "fixed"
            );

    public static final List<String> KEYS = Arrays.asList(
            "background",
            "font",
            "min",
            "max",
            "inline",
            "border",
            "box",
            "static",
            "z",
            "justify",
            "align",
            "ist",
            "text");

    public static final List<String> COMBINED_RULES = Arrays.asList(
            "border-top",
            "border-bottom",
            "border-left",
            "border-right",
            "background-color",
            "font-size",
            "font-weight",
            "font-family",
            "font-align",
            "min-width",
            "min-height",
            "max-width",
            "max-height",
            "inline-block",
            "border-color",
            "border-style",
            "border-width",
            "box-sizing",
            "border-box",
            "z-index",
            "justify-content",
            "align-items",
            "border-radius",
            "ist-style",
            "text-align",
            "box-shadow");

    public Rule (LexicalAnalyzer lexicalAnalyzer){
        super.initVars(lexicalAnalyzer);
    }

    public void saveToken(){
        tokens.add( new Token(
                possibleToken.getPossibleToken(),
                "Reglas",
                possibleToken.getPossibleToken(),
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }

    public boolean isToken() {

        return (RULES.contains(possibleToken.getPossibleToken()) || COMBINED_RULES.contains(possibleToken.getPossibleToken()));

    }

    public boolean isKey(){
        return (KEYS.contains(possibleToken.getPossibleToken()) && input[index.get() + 1] == '-');
    }
}
