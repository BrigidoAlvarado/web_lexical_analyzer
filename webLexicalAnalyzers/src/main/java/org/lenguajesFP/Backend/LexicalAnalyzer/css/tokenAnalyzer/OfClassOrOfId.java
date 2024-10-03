package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class OfClassOrOfId extends LexicalAnalyzer {

    public static final String TYPE_CLASS = "De Clase";
    public static final String TYPE_ID = "De Id";

    private  char initChar;
    private boolean isToken = false;
    private char[] chars;
    private int count = 0;
    private String type;

    public OfClassOrOfId(LanguageTypeAnalyzer languageTypeAnalyzer) {
        super.initVars(languageTypeAnalyzer);
    }

    public boolean isToken(char initChar){
        this.initChar = initChar;
        serType();
        chars = possibleToken.getPossibleToken().toCharArray();
        count = 0;
        try{
            initState();
        }catch (ArrayIndexOutOfBoundsException e){
            return isToken;
        }
        return isToken;
    }

    private void initState(){
        if (chars[count] == initChar){
            count++;
            pointState();
        }else{
            isToken = false;
        }
    }

    private void pointState(){
        if (isLessLetter(chars[count])){
            isToken = true;
            count++;
            acceptanceStatus();
        }else{
            isToken = false;
        }
    }

    private void acceptanceStatus(){
        if (isLessLetterOrNumber(chars[count]) || chars[count] == '-'){
            isToken = true;
            count++;
            acceptanceStatus();
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
                index.getRow() - 1,
                index.getColumn() - 1)
        );
        outputCode.add(possibleToken.getPossibleToken());
    }

    private void serType(){
        if (initChar == '.'){
            type = TYPE_CLASS;
        } else if (initChar == '#'){
            type = TYPE_ID;
        }
    }

}
