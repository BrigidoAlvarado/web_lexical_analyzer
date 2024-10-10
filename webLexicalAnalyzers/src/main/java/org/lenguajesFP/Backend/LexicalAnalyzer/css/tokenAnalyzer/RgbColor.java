package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;

public class RgbColor extends LanguageTypeAnalyzer {

    public static final String KEYWORD = "rgba";
    private boolean isToken ;

    public RgbColor(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
    }

    public boolean isToken(){
        isToken = false;
        initState();
        return isToken;
    }

    private void initState(){
        if (possibleToken.getPossibleToken().equalsIgnoreCase(KEYWORD)){
            next();
            keyStatus();
        }
    }

    private void keyStatus(){
        if (current() == '('){
            concat();
            next();
            par1Status();
        }
    }

    private void par1Status(){
        if (isNumber(current())){
            concat();
            next();
            number1();
        } else if (isSpace(current())){
            concat();
            next();
            par1Status();
        }else{
            errorStatus();
        }
    }

    private void number1(){
        if (current() == ','){
            concat();
            next();
            comma1();
        } else if (isSpace(current())){
            concat();
            next();
            number1();
        }else{
            errorStatus();
        }
    }

    private void comma1(){
        if (isNumber(current())){
            concat();
            next();
            number2();
        } else if (isSpace(current())){
            concat();
            next();
            comma1();
        }else{
            errorStatus();
        }
    }

    private void number2(){
        if (current() == ','){
            concat();
            next();
            comma2();
        } else if (isSpace(current())) {
            concat();
            next();
            number2();
        }else{
            errorStatus();
        }
    }

    private void comma2(){
        if (isNumber(current())){
            concat();
            next();
            number3();
        }else if (isSpace(current())){
            concat();
            next();
            comma2();
        }else{
            errorStatus();
        }
    }

    private void number3 (){
        if (current() == ')'){
            concat();
            next();
            finalStatus();
        } else if (current() == ',') {
            concat();
            next();
            comma3();
        } else if (isSpace(current())) {
            concat();
            next();
            number3();
        }else{
            errorStatus();
        }
    }

    private void finalStatus(){
        isToken = true;
    }

    private void comma3(){
        if (isNumber(current())){
            concat();
            next();
            number4();
        } else if (isSpace(current())){
            concat();
            next();
            comma3();
        }else{
            errorStatus();
        }
    }

    private void number4(){
        if (current() == '.'){
            concat();
            next();
            point();
        } else if (isNumber(current())){
            concat();
            next();
            number4();
        } else if (isSpace(current())){
            concat();
            next();
            number4();
        }else{
            errorStatus();
        }
    }

    private void point(){
        if (isNumber(current())){
            concat();
            next();
            number5();
        }else if(isSpace(current())){
            concat();
            next();
            point();
        }else{
            errorStatus();
        }
    }

    private void number5(){
        if (current() == ')'){
            concat();
            next();
            finalStatus();
        } else if (isNumber(current())) {
            concat();
            next();
            number5();
        }else{
            errorStatus();
        }
    }


    private void errorStatus(){
        if (isSpace(current())){
            saveErrorToken();
        } else{
            concat();
            next();
        }
    }
    private void saveErrorToken(){
        errors.add(new TokenError(
                possibleToken.getPossibleToken(),
                null,
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        possibleToken.reStart();
    }

    public void saveToken(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                "Colores",
                "^rgba\\(\\s*[0-9]\\s*,\\s*[0-9]\\s*,\\s*[0-9]\\s*(,\\s*(0|1|0?\\.[0-9])\\s*)?\\)$",
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(possibleToken.getPossibleToken());
        possibleToken.reStart();
    }
}

