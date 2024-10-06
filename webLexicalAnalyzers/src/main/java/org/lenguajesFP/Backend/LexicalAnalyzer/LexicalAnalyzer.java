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
    protected List<String> htmlTokens;
    protected List<String> cssTokens;
    protected List<String> jsTokens;
    protected List<Token> tokens;
    protected List<Token> errors;
    protected List<String> outputCode;
    protected PossibleToken possibleToken;

    private final List<String> letters = new ArrayList<>(Arrays.asList(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    ));

    private final List<String> capitalLetters = new ArrayList<>(Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    ));

    private final List<String> numbers = new ArrayList<>(Arrays.asList("0","1","2","3","4","5","6","7","8","9"));


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
        htmlTokens = analyzer.getHtmlTokens();
        cssTokens = analyzer.getCssTokens();
        jsTokens = analyzer.getJsTokens();

    }

    protected String saveCurrentChar(String output){
        if (output == null){
            return String.valueOf(input[index.get()]);
        } else {
            return output += input[index.get()];
        }
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

    public List<String> getHtmlTokens() {
        return htmlTokens;
    }

    public List<String> getCssTokens() {
        return cssTokens;
    }

    public List<String> getJsTokens() {
        return jsTokens;
    }

    public boolean isLessLetter(char c){
        return letters.contains(String.valueOf(c));
    }

    protected boolean isLessLetterOrNumber(char c){
        return letters.contains(String.valueOf(c)) || numbers.contains(String.valueOf(c));
    }

    public boolean isLetter(char c){
        return letters.contains(String.valueOf(c)) || capitalLetters.contains(String.valueOf(c));
    }

    protected char current(){
        return input[index.get()];
    }


    protected void readExceptionToken(){
        //if ()
    }
}
