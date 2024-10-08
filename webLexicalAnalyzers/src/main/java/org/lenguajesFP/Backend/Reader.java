package org.lenguajesFP.Backend;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.ArrayList;
import java.util.List;

public class Reader {

    protected List<Token> tokens = new ArrayList<>();
    protected List<Token> errors = new ArrayList<>();
    protected List<String> htmlTokens = new ArrayList<>();
    protected List<String> cssTokens = new ArrayList<>();
    protected List<String> jsTokens = new ArrayList<>();
    protected List<String> outputCode;
    char[] input;
    Index index = new Index();

    public void readCode(String input) throws LexicalAnalyzerException {
        this.input = (input + "     ").toCharArray();
        LanguageTypeAnalyzer analyzer = new LanguageTypeAnalyzer();
         analyzer.read(
                tokens,
                errors,
                outputCode
                ,this.input,
                index,
                htmlTokens,
                cssTokens,
                jsTokens);
    }
    
    public List<String> getHtmlTokens(){
        return htmlTokens;
    }
    
    public List<String> getCssTokens(){
        return cssTokens;
    }
    
    public List<String> getJsTokens(){
        return  jsTokens;
    }
}