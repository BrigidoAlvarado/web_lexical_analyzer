package org.lenguajesFP.Backend.LexicalAnalyzer.js;

import org.lenguajesFP.Backend.LexicalAnalyzer.IntegerAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

public class JsAnalyzer extends LexicalAnalyzer {


    private CharacterTokenAnalyzer characterTokenAnalyzer;
    private TwoCharacterAnalyzer twoCharacterAnalyzer;
    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private IntegerAnalyzer integerAnalyzer;

    public void readJs(LanguageTypeAnalyzer languageTypeAnalyzer) throws LexicalAnalyzerException {
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        super.initVars(this.languageTypeAnalyzer);
        characterTokenAnalyzer = new CharacterTokenAnalyzer(languageTypeAnalyzer);
        twoCharacterAnalyzer = new TwoCharacterAnalyzer(languageTypeAnalyzer);
        integerAnalyzer = new IntegerAnalyzer(languageTypeAnalyzer);
        initStatus();
    }

    public void initStatus()throws LexicalAnalyzerException {
        if (current() == '>' && input[index.get() + 1] == '>'){//cambio de estado
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index, htmlTokens, cssTokens, jsTokens);
        } else if (isSpace(current())){
            outputCode.add(String.valueOf(current()));
            next();
            initStatus();
        }if (possibleToken.getPossibleToken() != null){
            if (integerAnalyzer.isToken()) {
                integerAnalyzer.saveToken("Javascript");
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
