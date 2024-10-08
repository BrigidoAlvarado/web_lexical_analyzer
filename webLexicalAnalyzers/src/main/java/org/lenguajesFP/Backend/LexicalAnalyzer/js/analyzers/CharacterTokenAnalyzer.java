package org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.enums.CharacterToken;
import org.lenguajesFP.Backend.Token;

public class CharacterTokenAnalyzer extends LexicalAnalyzer {

    private String tokenType;

    public CharacterTokenAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
    }

    public boolean isToken(){
            tokenType = CharacterToken.Coma.getEnumNameFromChar(current());
            if(tokenType == null){
                return false;
            }
            return !isException();

    }

    private boolean isException(){
        System.out.println("actual "+current());
        System.out.println("evaluando "+ input[index.get()+1]);
        System.out.println((input[index.get() + 1] == '+'
                || input[index.get() + 1] == '-'
                || input[index.get() + 1] == '='));
        return (input[index.get() + 1] == '+'
                || input[index.get() + 1] == '-'
                || input[index.get() + 1] == '=');
    }

    public void saveToken(){
        tokens.add( new Token(
                String.valueOf(current()),
                tokenType,
                String.valueOf(current()),
                "Javascript",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(String.valueOf(current()));
        possibleToken.reStart();
    }
}

