package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class OfClassOrOfId extends LexicalAnalyzer {

    public static final String TYPE_CLASS = "De Clase";
    public static final String TYPE_ID = "De Id";

    private  char initChar;
    private boolean isToken = false;
    private String type;
    private int saveIndex;
    private int saveRow;
    private int saveCol;

    private final Other other;
    private final Universal universal;
    private final Combinator combinator;

    public OfClassOrOfId(LanguageTypeAnalyzer languageTypeAnalyzer) {
        super.initVars(languageTypeAnalyzer);
        other = new Other(languageTypeAnalyzer);
        universal = new Universal(languageTypeAnalyzer);
        combinator = new Combinator(languageTypeAnalyzer);
    }

    public boolean isToken(char initChar){
        saveIndex = index.get();
        saveRow = index.getRow();
        saveCol = index.getColumn();
        isToken = false;
        this.initChar = initChar;
        setType();
        initState();
        /*if (isToken == false && initChar == '#'){
            possibleToken.reStart();
            index.reStart(saveIndex,saveRow,saveCol);
        }*/
        return isToken;
    }

    private void initState(){
        if (current() == initChar){
            concat();
            next();
            pointState();
        }else{
            isToken = false;
        }
    }

    private void pointState(){
        if (isLessLetter(current())){
            concat();
            isToken = true;
            next();
            acceptanceStatus();
        }else{
            isToken = false;
        }
    }

    private void acceptanceStatus(){
        if (isLessLetterOrNumber(current()) || current() == '-'){
            concat();
            isToken = true;
            next();
            acceptanceStatus();
        } else if (isSpace(current()) || combinator.isCharacterToken() || other.isCharacterToken() || universal.isCharacterToken()) {
            isToken = true;
        } else{
            isToken = false;
        }
    }

    public void saveToken(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                type,
                possibleToken.getPossibleToken(),
                "CSS",
                index.getRow() ,
                index.getColumn() )
        );
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }

    private void setType(){
        if (initChar == '.'){
            type = TYPE_CLASS;
        } else if (initChar == '#'){
            type = TYPE_ID;
        }
    }

}
