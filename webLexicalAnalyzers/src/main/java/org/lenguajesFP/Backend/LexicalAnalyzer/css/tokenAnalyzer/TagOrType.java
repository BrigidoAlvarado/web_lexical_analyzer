package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagOrType extends LexicalAnalyzer{

    public final static List<String> TAG_OR_TYPE = List.of(
            "body",
            "header",
            "main",
            "nav",
            "aside",
            "div",
            "ul",
            "ol",
            "li",
            "a",
            "h1",
            "h2",
            "h3",
            "h4",
            "h5",
            "h6",
            "p",
            "span",
            "label",
            "textarea",
            "button",
            "section",
            "article",
            "footer");

    public static final String TYPE = "Etiqueta o Tipo";

    public TagOrType(LexicalAnalyzer languageTypeAnalyzer) {
        super.initVars(languageTypeAnalyzer);
    }

    public boolean isToken(){
        if (TAG_OR_TYPE.contains(possibleToken.getPossibleToken())){
            if (possibleToken.getPossibleToken().equalsIgnoreCase("a") && isLetter(input[index.get() + 1])){
                return false;
            }
            if (possibleToken.getPossibleToken().equalsIgnoreCase("p") && isLetter(input[index.get() + 1])){
                return false;
            }
            return true;
        }
        return false;
    }

    public void saveToken(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                TYPE,
                possibleToken.getPossibleToken(),
                "CSS",
                index.getRow(),
                index.getColumn())
        );
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }
}

