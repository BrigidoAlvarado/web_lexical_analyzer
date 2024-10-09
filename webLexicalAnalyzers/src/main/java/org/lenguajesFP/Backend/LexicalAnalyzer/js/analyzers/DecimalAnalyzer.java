package org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class DecimalAnalyzer extends LexicalAnalyzer {

    private boolean isToken;
    private CharacterTokenAnalyzer characterTokenAnalyzer;

    public DecimalAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
        characterTokenAnalyzer = new CharacterTokenAnalyzer(lexicalAnalyzer);
    }

    public boolean isToken(){
        isToken = false;
        initStatus();
        return isToken;
    }

    private void initStatus() {
        if (isNumber(current())){
            concat();
            next();
            numberStatus();
        }
    }

    private void numberStatus(){
        if (isNumber(current())){
            concat();
            next();
            numberStatus();
        } else if (current() == '.'){
            concat();
            next();
            pointStatus();
        }
    }

    private void pointStatus(){
        if (isNumber(current())){
            concat();
            next();
            decimalStatus();
        }
    }

    private void decimalStatus(){
        if (isNumber(current())){
            concat();
            next();
            decimalStatus();
        } else if (isSpace(current()) || (characterTokenAnalyzer.isToken())) {
            isToken = current() != '.' && current() != ',' ;
        }
    }

    public void saveToken(){
        tokens.add( new Token(
                possibleToken.getPossibleToken(),
                "Decimal",
                "[0-9]+ . [0-9]+",
                "Javascript",
                index.getRow(),
                index.getColumn()-1
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }

}
