package org.lenguajesFP.Backend.LexicalAnalyzer.js;

import org.lenguajesFP.Backend.LexicalAnalyzer.*;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers.CharacterTokenAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers.DecimalAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers.IntegerJsAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.js.analyzers.TwoCharacterAnalyzer;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

public class JsAnalyzer extends LexicalAnalyzer {


    private CharacterTokenAnalyzer characterTokenAnalyzer;
    private TwoCharacterAnalyzer twoCharacterAnalyzer;
    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private IntegerJsAnalyzer integerAnalyzer;
    private DecimalAnalyzer decimalAnalyzer;

    public void readJs(LanguageTypeAnalyzer languageTypeAnalyzer) throws LexicalAnalyzerException {
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        super.initVars(this.languageTypeAnalyzer);
        characterTokenAnalyzer = new CharacterTokenAnalyzer(languageTypeAnalyzer);
        twoCharacterAnalyzer = new TwoCharacterAnalyzer(languageTypeAnalyzer);
        integerAnalyzer = new IntegerJsAnalyzer(languageTypeAnalyzer);
        decimalAnalyzer = new DecimalAnalyzer(languageTypeAnalyzer);
        initStatus();
    }

    public void initStatus()throws LexicalAnalyzerException {
        if (current() == '>' && input[index.get() + 1] == '>'){//cambio de estado
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index, htmlTokens, cssTokens, jsTokens);
        }  else if (input[index.get() + 1] == '/' && current() == '/'){//cometario
            CommentAnalyzer commentAnalyzer = new CommentAnalyzer(languageTypeAnalyzer);
            commentAnalyzer.readComment("Javascript");
            initStatus();

        } else if (isSpace(current())){
            outputCode.add(String.valueOf(current()));
            next();
            initStatus();
        }if (possibleToken.getPossibleToken() != null && isNumber(current())){
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
        }else {
            concat();
            characterStatus();
        }
    }

    private void characterStatus() throws LexicalAnalyzerException {
          if (characterTokenAnalyzer.isToken()){
            characterTokenAnalyzer.saveToken();
            next();
            initStatus();
        } else if (possibleToken.getPossibleToken()!= null){
              isPossibleToken();
          }
    }

    private void isPossibleToken()throws LexicalAnalyzerException {
                next();
                concat();
                isDoubleCharacterToken();
    }

    private void isDoubleCharacterToken() throws LexicalAnalyzerException {
        System.out.println("evaluando en poosTOk "+possibleToken.getPossibleToken());
            if (twoCharacterAnalyzer.isToken()) {
                twoCharacterAnalyzer.saveToken();
                next();
                initStatus();
            } else{
                initStatus();
            }
    }
}
