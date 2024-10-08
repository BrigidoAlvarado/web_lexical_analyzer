package org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.enums.TwoCharacterToken;
import org.lenguajesFP.Backend.Token;

public class TwoCharacterAnalyzer extends LexicalAnalyzer{

    private String tokenType;

    public TwoCharacterAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
    }

    public boolean isToken(){
        if (possibleToken.getPossibleToken() != null){
            tokenType = TwoCharacterToken.And.getEnumNameFromChar(possibleToken.getPossibleToken());
            return tokenType != null;
        }
        return false;
    }

    public void saveToken(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                tokenType,
                possibleToken.getPossibleToken(),
                "Javascript",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }
}
