package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;

import java.util.List;

public class LexicalAnalyzer {

    protected int index = 0;
    protected int row = 1;
    protected int column = 1;
    protected char[] input;
    protected List<Token> tokens;
    protected List<Token> errors;
    protected List<String> outputCode;
    protected String possibleToken;

    protected boolean isSpace(char c){
        return c == ' ' || c == '\n' || c == '\r';
    }

    protected void next(){
        if (input[index] == '\n'){
            column++;
        } else if (input[index] == '\r' || input[index] == ' '){
            row++;
        }
        index++;
    }

    protected void concat(){
        if (possibleToken == null){
            possibleToken = String.valueOf(input[index]);
        } else {
            possibleToken += input[index];
        }
    }
}
