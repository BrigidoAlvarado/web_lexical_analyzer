package org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

import java.util.List;

public class ReservedWordsAnalyzer extends LexicalAnalyzer {

    public static final String RESERVED_WORD = "Palabra reservada";
    public static final String BOOLEAN = "Booleano";
    public static final String KEYWORD = "console";
    public static final List<String> RESERVED_WORDS = List.of(
            "function",
            "const",
            "let",
            "document",
            "event",
            "alert",
            "for",
            "while",
            "if",
            "else",
            "return",
            "console.log",
            "null");
    public static final List<String> BOOLEAN_RESERVED_WORDS = List.of(
            "true",
            "false");

    private CharacterTokenAnalyzer characterTokenAnalyzer;

    public ReservedWordsAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
        this.characterTokenAnalyzer = new CharacterTokenAnalyzer(lexicalAnalyzer);
    }

    public boolean isKey(){
        return possibleToken.getPossibleToken().equals(KEYWORD);
    }

    public boolean isReservedWord(){
        return RESERVED_WORDS.contains(possibleToken.getPossibleToken());
    }

    public boolean isBoolean(){
        return BOOLEAN_RESERVED_WORDS.contains(possibleToken.getPossibleToken());
    }

    public void saveReservedWord(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                RESERVED_WORD,
                possibleToken.getPossibleToken(),
                "Javascript",
                index.getRow() - 1,
                index.getColumn() -1
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }

    public void saveBoolean(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                BOOLEAN,
                possibleToken.getPossibleToken(),
                "Javascript",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }

    public boolean validateFinalWord(){
        return (isSpace(input[index.get() + 1]));
    }
}
