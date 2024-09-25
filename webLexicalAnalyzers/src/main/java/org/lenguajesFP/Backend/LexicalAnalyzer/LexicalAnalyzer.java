package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;

import java.util.List;

public class LexicalAnalyzer {

    protected int current;
    protected char[] input;
    protected List<Token> tokens;
    protected List<Token> errors;
    protected List<String> code;

    protected boolean isSpace(char c){
        return c == ' ' || c == '\n' || c == '\r';
    }
}
