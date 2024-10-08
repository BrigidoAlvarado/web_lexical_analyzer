package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;

public class IntegerAnalyzer extends LexicalAnalyzer{

    public IntegerAnalyzer(LexicalAnalyzer lexicalAnalyzer){
        super.initVars(lexicalAnalyzer);
    }

    public boolean isToken(){
        boolean isToken = false;
        if (initState()){
            char[] numbers = possibleToken.getPossibleToken().toCharArray();
            for (char c : numbers){
                isToken = isNumber(c);
                if (!isToken){
                    break;
                }
            }
        }

        return isToken;
    }

    private boolean initState(){
        return (!isNumber(input[index.get() + 1]));
    }

    public void saveToken(String language){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                "Enteros",
                "[0-9]+",
                language,
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }
}
