package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class HexadecimalColor extends LexicalAnalyzer {

    public static final char INIT_TOKEN = '#';
    public static final String REGULAR_EXPRESSION = "#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})";

    private final Combinator combinator;
    private final Universal universal;
    private final Other other;

    private boolean isToken = false;
    private int indexSave;
    private int rowSave;
    private int columnSave;
    private String savePossibleToken;

    public HexadecimalColor(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
        combinator = new Combinator(lexicalAnalyzer);
        universal = new Universal(lexicalAnalyzer);
        other = new Other(lexicalAnalyzer);

    }

    public boolean isToken(){
        isToken = false;
        indexSave = index.get();
        rowSave = index.getRow();
        columnSave = index.getColumn();
        savePossibleToken = possibleToken.getPossibleToken();
        if (current() == INIT_TOKEN){
            concat();
            next();
            numeralStatus();
        }
        if (!isToken){
            index.reStart(indexSave, rowSave, columnSave);
            possibleToken.setPossibleToken(savePossibleToken);
        }
        return isToken;
    }

    private void numeralStatus(){
        if (isLetter(current()) || isNumber(current())){
            concat();
            next();
            f1Status();
        }
    }
    
    private void f1Status(){
        if (isLetter(current()) || isNumber(current())){
            concat();
            next();
            f2Status();
        }
    }
    
    private void f2Status(){
        if (isLetter(current()) || isNumber(current())) {
            concat();
            next();
            f3Status();
        }
    }

    private void f3Status(){
        if (other.isCharacterToken() ||
                combinator.isCharacterToken() ||
                universal.isCharacterToken() ||
                isSpace(current())){
                isToken = true;
        } else if (isLetter(current()) || isNumber(current())) {
            concat();
            next();
            f4Status();
        }
    }

    private void f4Status(){
        if (isLetter(current()) || isNumber(current())) {
            concat();
            next();
            f5Status();
        }
    }

    private void f5Status(){
        if (isLetter(current()) || isNumber(current())) {
            concat();
            next();
            f6Status();
        }
    }

    private void f6Status(){
        if (other.isCharacterToken() ||
                combinator.isCharacterToken() ||
                universal.isCharacterToken() ||
                isSpace(current())){
            isToken = true;
        }
    }

    public void saveToken(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                "Colores",
                REGULAR_EXPRESSION,
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }
}
