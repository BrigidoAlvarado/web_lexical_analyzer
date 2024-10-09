package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Index;
import org.lenguajesFP.Backend.PossibleToken;

public class Optimizer extends LexicalAnalyzer{

    private String result;

    public String optimizeCode(String input){
        result = null;
        possibleToken = new PossibleToken();
        possibleToken.reStart();
        index = new Index();
        index.reStart(0,0,0);
        this.input = input.toCharArray();
        try {
            initState();
        }catch (ArrayIndexOutOfBoundsException e){
            delete();
            return result;
        }
        return result;
    }

    private void initState(){
        if (current() == '\n'){
            concat();
            analyzeStatus();
        }else{
            concat();
            next();
            initState();
        }
    }

    private void analyzeStatus(){
        delete();
        possibleToken.reStart();
        next();
        initState();
    }

    private boolean deleteRow(){
        char [] chars = possibleToken.getPossibleToken().toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '/' && chars[i+1] == '/'){ return true;}
        }

        for (char aChar : chars) {
            if (!isSpace(aChar)) {
                return false;
            }
        }
        return true;
    }

    private void delete(){
        if (possibleToken.getPossibleToken() != null){
            if (!deleteRow()) {
                //guardo la linea
                if (result == null) {
                    result = possibleToken.getPossibleToken();
                } else {
                    result += possibleToken.getPossibleToken();
                }
            }
        }
    }
}
