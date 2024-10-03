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
    private OfClassOrOfId ofClass;
    private LanguageTypeAnalyzer languageTypeAnalyzer;

    public void readCss(LanguageTypeAnalyzer languageTypeAnalyzer) throws LexicalAnalyzerException {
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        initVars(this.languageTypeAnalyzer);
        Combinator combinator = new Combinator(this.languageTypeAnalyzer);
        Other other = new Other(this.languageTypeAnalyzer);
        Rule rule = new Rule();
        TagOrType tagOrType = new TagOrType(this.languageTypeAnalyzer);
        Universal universal = new Universal(this.languageTypeAnalyzer);
        OfClassOrOfId ofClass = new OfClassOrOfId(this.languageTypeAnalyzer);
        initState();
    }

    private void initState() throws LexicalAnalyzerException {

         if (input[index.get()] == '>'){
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index);
        } else if (input[index.get()] == '/'){
            CommentAnalyzer commentAnalyzer = new CommentAnalyzer(languageTypeAnalyzer);
            commentAnalyzer.readComment("CSS");
            initState();
        } else if (isSpace(input[index.get()]) && current() != ' '){
             outputCode.add(String.valueOf(input[index.get()]));
             next();
             initState();
         } else {
            characterState();
        }
    }

    private void characterState() throws LexicalAnalyzerException {

        if (combinator.isToken()){ //el caracter puede ser un token del tipo combinador
            isException();
            combinator.saveToken();
            next();
            initState();
        } else if (other.isToken()){// el caraceter puede ser un token del tipo otros
            isException();
            other.saveToken();
            next();
            initState();
        } else if (universal.isToken()){// el caracter puede ser un token del tipo universal
            isException();
            universal.saveToken();
            next();
            initState();
        } else if (isSpace(current()) && possibleToken.getPossibleToken() != null){//termina la palabra y esta puede ser un token
            isException();
        } else if (isSpace(current())){// si el espacio no es token
            outputCode.add(String.valueOf(current()));
            next();
            initState();
        }else {
            concat();
            next();
            characterState();
        }
    }

    private void isException(){

    }

    private void isPossibleToken() throws LexicalAnalyzerException {
        if (possibleToken.getPossibleToken() != null){
            if (tagOrType.isToken()){
                tagOrType.saveToken();
            } else if (ofClass.isToken('.')){
                ofClass.saveToken();
            } else if (ofClass.isToken('#')) {
                ofClass.saveToken();
            }
        }
    }
}
