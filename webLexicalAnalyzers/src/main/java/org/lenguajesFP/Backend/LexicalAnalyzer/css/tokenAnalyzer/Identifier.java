package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class Identifier extends LexicalAnalyzer {

    private final Combinator combinator;
    private final Universal universal;
    private final Other other;
    private final TagOrType tagOrType;
    private final Rule rule;

    private int indexSave;
    private int rowSave;
    private int columnSave;
    private boolean isToken;
    private String savePossibleToken;

    public Identifier(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
        this.combinator = new Combinator(lexicalAnalyzer);
        this.universal = new Universal(lexicalAnalyzer);
        this.other = new Other(lexicalAnalyzer);
        this.tagOrType = new TagOrType(lexicalAnalyzer);
        this.rule = new Rule(lexicalAnalyzer);
    }

    public boolean isToken(){
        isToken = false;
        indexSave = index.get();
        rowSave = index.getRow();
        columnSave = index.getColumn();
        savePossibleToken = possibleToken.getPossibleToken();
        initSate();
        if (!isToken){
            index.reStart(indexSave, rowSave, columnSave);
            possibleToken.setPossibleToken(savePossibleToken);
        }
        return isToken;
    }

    private void initSate(){
        if (isSpace(input[index.get() - 1])){
            if (isLessLetter(current())){
                next();
                initLetter();
            }
        }
    }

    private void initLetter(){
        if (isLessLetter(current())){
            concat();
            next();
            initLetter();
        } else if (isNumber(current())){
            concat();
            next();
            initNumber();
        } else if (current() == '-') {
            concat();
            next();
            scriptStatus();
        } else if (combinator.isCharacterToken() || universal.isCharacterToken() || other.isCharacterToken() || isSpace(current())){
            finalState();
        }
    }

    private void initNumber(){
        if (isNumber(current())){
            concat();
            next();
            initNumber();
        } else if (current() == '-'){
            concat();
            next();
            scriptStatus();
        }else if (combinator.isCharacterToken() || universal.isCharacterToken() || other.isCharacterToken() || isSpace(current())){
            finalState();
        }
    }

    private void scriptStatus(){
        if (isLessLetter(current())){
            concat();
            next();
            letter();
        } else if (isNumber(current())) {
            concat();
            next();
            number();
        }
    }

    private void letter(){
        if (isLessLetter(current())){
            concat();
            next();
            letter();
        } else if (isNumber(current())) {
            concat();
            next();
            number();
        } else if (current() == '-') {
            concat();
            next();
            scriptStatus();
        } else if (isSpace(current()) || combinator.isCharacterToken() || universal.isCharacterToken() || other.isCharacterToken()){
            finalState();
        }
    }

    private void number(){
        if (isLessLetter(current())){
            concat();
            next();
            letter();
        } else if (isNumber(current())) {
            concat();
            next();
            number();
        } else if (current() == '-') {
            concat();
            next();
            scriptStatus();
        } else if (combinator.isCharacterToken() || universal.isCharacterToken() || other.isCharacterToken() || isSpace(current())){
            finalState();
        }
    }

    public void saveToken (){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                "Identificador",
                "[a-z]+ [0-9]* (- ([a-z] | [0-9])+)*",
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }

    private void finalState(){
        isToken = (!rule.isToken() &&
                !rule.isKey() &&
                !other.isToken() &&
                !tagOrType.isToken() &&
                !possibleToken.getPossibleToken().equalsIgnoreCase("rgba"));
    }
}
