package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Index;
import org.lenguajesFP.Backend.PossibleToken;
import org.lenguajesFP.Backend.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexicalAnalyzer {

    protected Index index;
    protected int row = 1;
    protected int column = 1;
    protected char[] input;
    protected List<Token> tokens;
    protected List<Token> errors;
    protected List<String> outputCode;
    protected PossibleToken possibleToken;

    private List<String> letters = new ArrayList<>(Arrays.asList(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    ));

    private List<String> capitalLetters = new ArrayList<>(Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    ));


    protected boolean isSpace(char c){
        return c == ' ' || c == '\n' || c == '\r';
    }

    protected void next(){
        if (input[index.get()] == '\n'){
            index.increaseRow();
            row = index.getRow();
        } else{
            index.increaseColumn();
            row = index.getColumn();
        }
        index.increase();
    }

    protected void concat(){
        possibleToken.concat(input[index.get()]);
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

    public Index getIndex() {
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

    public PossibleToken getPossibleToken() {
        return possibleToken;
    }

    public boolean isLessLetter(char c){
        return letters.contains(String.valueOf(c));
    }

    public boolean isLetter(char c){
        return letters.contains(String.valueOf(c)) || capitalLetters.contains(String.valueOf(c));
    }
}
