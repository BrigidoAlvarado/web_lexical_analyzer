package org.lenguajesFP.Backend;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.ArrayList;
import java.util.List;

public class Reader {

    protected List<Token> tokens = new ArrayList<>();
    protected List<Token> errors = new ArrayList<>();
    protected List<String> outputCode = new ArrayList<>();
    char[] input;
    Index index = new Index();

    public List<String> readCode(String input) throws LexicalAnalyzerException {
        this.input = input.toCharArray();
        LanguageTypeAnalyzer analyzer = new LanguageTypeAnalyzer();
        return analyzer.read(tokens,errors,outputCode,this.input,index);
    }
}