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

    protected void initVars(LexicalAnalyzer analyzer){
        tokens = analyzer.getTokens();
        errors = analyzer.getErrors();
        outputCode = analyzer.getOutputCode();
        index = analyzer.getIndex();
        row = analyzer.getRow();
        column = analyzer.getColumn();
        possibleToken = analyzer.getPossibleToken();
        input = analyzer.getInput();

    }

    public int getIndex() {
        return index;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char[] getInput() {
        return input;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public List<Token> getErrors() {
        return errors;
    }

    public List<String> getOutputCode() {
        return outputCode;
    }

    public String getPossibleToken() {
        return possibleToken;
    }
}
