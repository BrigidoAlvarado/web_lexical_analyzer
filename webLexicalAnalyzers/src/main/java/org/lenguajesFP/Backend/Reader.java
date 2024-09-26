package org.lenguajesFP.Backend;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class Reader {

    protected List<Token> tokens = new ArrayList<>();
    protected List<Token> errors = new ArrayList<>();
    protected List<String> outputCode =  new ArrayList<>();
    char[] input;
    int index = 0;

    public void readCode(String input){
        this.input = input.toCharArray();
        LanguageTypeAnalyzer analyzer = new LanguageTypeAnalyzer();
        analyzer.read(tokens,errors,outputCode,this.input,index);
    }
}