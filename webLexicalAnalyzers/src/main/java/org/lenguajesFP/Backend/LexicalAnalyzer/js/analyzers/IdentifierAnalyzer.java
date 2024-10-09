package org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class IdentifierAnalyzer extends LexicalAnalyzer {

    private int counter ;
    private boolean isToken;
    private char[] chars;
    private CharacterTokenAnalyzer characterTokenAnalyzer;

    @Override
    protected char current(){
        return chars[counter];
    }

    public IdentifierAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
        characterTokenAnalyzer = new CharacterTokenAnalyzer(lexicalAnalyzer);
    }

    public boolean isToken(){
        isToken = false;
        counter = 0;
        chars = possibleToken.getPossibleToken().toCharArray();
        try {
            initStatus();
            return isToken;
        }catch (ArrayIndexOutOfBoundsException e){
            return isToken;
        }
    }

    private void initStatus(){
        if (isLetter(current())){
            isToken = true;
            counter++;
            identifierStatus();
        }
    }

    private void identifierStatus(){
        if (isLetter(current()) || isNumber(current()) || current() == '_'){
            isToken = true;
            counter++;
            identifierStatus();
        } else {
            isToken = false;
        }
    }

    public void saveToken(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                "Identificador",
                "[a-zA-Z]([a-zA-Z] | [0-9] | [ _ ])*",
                "Javascript",
                index.getRow() ,
                index.getColumn() - 1
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }
}
