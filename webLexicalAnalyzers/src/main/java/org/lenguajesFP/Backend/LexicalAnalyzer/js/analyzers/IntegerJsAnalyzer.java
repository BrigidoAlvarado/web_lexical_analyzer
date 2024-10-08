package org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class IntegerJsAnalyzer extends LexicalAnalyzer {

    private boolean isToken;
    private CharacterTokenAnalyzer characterTokenAnalyzer;
    private int saveIndex;
    private int saveRow;
    private int saveColumn;
    private String savePossToken;


    public IntegerJsAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
        characterTokenAnalyzer = new CharacterTokenAnalyzer(lexicalAnalyzer);
    }

    public boolean isToken(){
        isToken = false;
        saveIndex = index.get();
        saveRow = index.getRow();
        saveColumn = index.getColumn();
        savePossToken = getPossibleToken().getPossibleToken();
        initStatus();
        if (!isToken){
            index.reStart(saveIndex,saveRow,saveColumn);
            possibleToken.setPossibleToken(savePossToken);
        }
        return isToken;
    }

    private void initStatus() {
        if (isNumber(current())){
            next();
            numberStatus();
        }
    }

    private void numberStatus(){
        if (isNumber(current())){
            concat();
            next();
            numberStatus();
        } else if (isSpace(current()) || characterTokenAnalyzer.isToken()) {
            isToken = current() != '.' && current() != ',' ;
        }
    }

    public void saveToken(){
        tokens.add( new Token(
                possibleToken.getPossibleToken(),
                "Entero",
                "[0-9]+",
                "Javascript",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }

}
