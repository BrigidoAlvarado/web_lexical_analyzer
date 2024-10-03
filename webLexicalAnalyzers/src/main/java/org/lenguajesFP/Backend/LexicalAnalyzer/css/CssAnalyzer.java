package org.lenguajesFP.Backend.LexicalAnalyzer.css;

import org.lenguajesFP.Backend.LexicalAnalyzer.CommentAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer.*;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

public class CssAnalyzer extends LexicalAnalyzer {

    private Combinator combinator;
    private Other other;
    private Rule rule;
    private TagOrType tagOrType;
    private Universal universal;
    private LanguageTypeAnalyzer languageTypeAnalyzer;

    public void readCss(LanguageTypeAnalyzer languageTypeAnalyzer) throws LexicalAnalyzerException {
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        initVars(this.languageTypeAnalyzer);
        Combinator combinator = new Combinator(this.languageTypeAnalyzer);
        Other other = new Other(this.languageTypeAnalyzer);
        Rule rule = new Rule();
        TagOrType tagOrType = new TagOrType();
        Universal universal = new Universal(this.languageTypeAnalyzer);
        initState();
    }

    private void initState() throws LexicalAnalyzerException {

        if (isSpace(input[index.get()])){
            outputCode.add(String.valueOf(input[index.get()]));
            next();
            outputCode.add(String.valueOf(input[index.get()]));
            initState();
        } else if (input[index.get()] == '>'){
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index);
        } else if (input[index.get()] == '/'){
            CommentAnalyzer commentAnalyzer = new CommentAnalyzer(languageTypeAnalyzer);
            commentAnalyzer.readComment("CSS");
            initState();
        } else {
            characterState();
        }
    }

    private void characterState() throws LexicalAnalyzerException {
        if (combinator.isToken()){
            combinator.saveToken();
        } else if (other.isToken()){
            //guardar el possible token si es que existe
            other.saveToken();
        } else if (universal.isToken()){
            //guardar el possible token si existe
            universal.saveToken();
        } else if (isSpace(current())){
            //validar si es un token
        }else {
            concat();
            next();
            characterState();
        }
    }

    private void savePossibleToken(){

    }
}
