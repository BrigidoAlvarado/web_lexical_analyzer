package org.lenguajesFP.Backend.LexicalAnalyzer.js;

import org.lenguajesFP.Backend.LexicalAnalyzer.*;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers.*;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

public class JsAnalyzer extends LexicalAnalyzer {

    private CharacterTokenAnalyzer characterTokenAnalyzer;
    private TwoCharacterAnalyzer twoCharacterAnalyzer;
    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private IntegerJsAnalyzer integerAnalyzer;
    private DecimalAnalyzer decimalAnalyzer;
    private ReservedWordsAnalyzer reservedWordsAnalyzer;

    public void readJs(LanguageTypeAnalyzer languageTypeAnalyzer) throws LexicalAnalyzerException {
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        super.initVars(this.languageTypeAnalyzer);
        characterTokenAnalyzer = new CharacterTokenAnalyzer(languageTypeAnalyzer);
        twoCharacterAnalyzer = new TwoCharacterAnalyzer(languageTypeAnalyzer);
        integerAnalyzer = new IntegerJsAnalyzer(languageTypeAnalyzer);
        decimalAnalyzer = new DecimalAnalyzer(languageTypeAnalyzer);
        reservedWordsAnalyzer = new ReservedWordsAnalyzer(languageTypeAnalyzer);
        initStatus();
    }

    public void initStatus()throws LexicalAnalyzerException {
        if (current() == '>' && input[index.get() + 1] == '>'){//cambio de estado
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index, htmlTokens, cssTokens, jsTokens);
        }  else if (input[index.get() + 1] == '/' && current() == '/'){//comentario
            CommentAnalyzer commentAnalyzer = new CommentAnalyzer(languageTypeAnalyzer);
            commentAnalyzer.readComment("Javascript");
            initStatus();
        } else if (isSpace(current()) && possibleToken.getPossibleToken() == null){
            outputCode.add(String.valueOf(current()));
            next();
            initStatus();
        }if (isNumber(current())){
            if (integerAnalyzer.isToken()){
                integerAnalyzer.saveToken();
                initStatus();
            } else if (decimalAnalyzer.isToken()){
                decimalAnalyzer.saveToken();
                initStatus();
            } else{
                System.out.println("se encontro un error");
            }
        } else if (current() == '`') {//cadena
            StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer,tokens,"Javascript",'`','`');
            try {
                if (stringAnalyzer.readString()){
                    outputCode.add(possibleToken.getPossibleToken());
                    possibleToken.reStart();
                    next();
                    initStatus();
                }
            }catch (InvalidTokenException e){
                errors.add(e.getError());
                next();
                initStatus();
            }
        } else if (current() == '\'') {//cadena
            StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer,tokens,"Javascript",'\'','\'');
            try {
                if (stringAnalyzer.readString()){
                    outputCode.add(possibleToken.getPossibleToken());
                    possibleToken.reStart();
                    next();
                    initStatus();
                }
            }catch (InvalidTokenException e){
                errors.add(e.getError());
                next();
                initStatus();
            }
        }else if (current() == '"') {//cadena
            StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer,tokens,"Javascript",'"','"');
            try {
                if (stringAnalyzer.readString()){
                    outputCode.add(possibleToken.getPossibleToken());
                    possibleToken.reStart();
                    next();
                    initStatus();
                }
            }catch (InvalidTokenException e){
                errors.add(e.getError());
                next();
                initStatus();
            }
        } else if (isFinalWord() ) {
            wordStatus();
        } else {
            concat();
            characterStatus();
        }
    }

    private void characterStatus() throws LexicalAnalyzerException {
          if (characterTokenAnalyzer.isToken()){
            characterTokenAnalyzer.saveToken();
            next();
            initStatus();
          }else {
              next();
              concat();
              isDoubleCharacterToken();
          }
    }

    private void isDoubleCharacterToken() throws LexicalAnalyzerException {

            if (twoCharacterAnalyzer.isToken()) {
                twoCharacterAnalyzer.saveToken();
                next();
                initStatus();
            } else if (reservedWordsAnalyzer.isKey()) {
                next();
                concat();
                next();
                initStatus();
            }  else {
                possibleToken.removeLastChar();
                initStatus();
            }
    }

    private void wordStatus()throws LexicalAnalyzerException {
        System.out.println("evaluando token final: <"+possibleToken.getPossibleToken()+">");
        if (reservedWordsAnalyzer.isReservedWord()){
            reservedWordsAnalyzer.saveReservedWord();
            initStatus();
        } else if (reservedWordsAnalyzer.isBoolean()) {
            reservedWordsAnalyzer.saveBoolean();
            initStatus();
        } else{
            System.out.println("se encontro un error al evaluar: <"+possibleToken.getPossibleToken()+">");
            possibleToken.reStart();
            initStatus();
        }
    }

    private boolean isFinalWord(){
        return (isSpace(current()) || characterTokenAnalyzer.isToken()) && possibleToken.getPossibleToken() != null;
    }


}
