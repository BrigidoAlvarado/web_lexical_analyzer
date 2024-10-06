package org.lenguajesFP.Backend.LexicalAnalyzer.css;

import org.lenguajesFP.Backend.LexicalAnalyzer.CommentAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.StringAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer.*;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
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
        combinator = new Combinator(this.languageTypeAnalyzer);
        other = new Other(this.languageTypeAnalyzer);
        rule = new Rule(this.languageTypeAnalyzer);
        tagOrType = new TagOrType(this.languageTypeAnalyzer);
        universal = new Universal(this.languageTypeAnalyzer);
        ofClass = new OfClassOrOfId(this.languageTypeAnalyzer);
        outputCode = cssTokens;
        initState();
    }

    private void initState() throws LexicalAnalyzerException {

         if (input[index.get()] == '>'){//cambio de estado
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index, htmlTokens, cssTokens, jsTokens);
        } else if (input[index.get()] == '/'){//cometario
            CommentAnalyzer commentAnalyzer = new CommentAnalyzer(languageTypeAnalyzer);
            commentAnalyzer.readComment("CSS");
            initState();
        } else if (isSpace(input[index.get()]) && current() != ' '){//tabulacion o salto de linea
             outputCode.add(String.valueOf(input[index.get()]));
             next();
             initState();
         } else if (current() == '`') {
             StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer,tokens,"CSS",'`','`');
             try {
                 stringAnalyzer.readString();
             }catch (InvalidTokenException e){
                 errors.add(e.getError());
                 next();
                 initState();
             }
         } else if (current() == '\'') {
             StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer,tokens,"CSS",'\'','\'');
             try {
                 stringAnalyzer.readString();
             }catch (InvalidTokenException e){
                 errors.add(e.getError());
                 next();
                 initState();
             }
         } else {//es un caracter
            characterState();
        }
    }

    private void characterState() throws LexicalAnalyzerException {

        if (combinator.isCharacterToken()){ //el caracter puede ser un token del tipo combinador
            isPossibleToken();
            combinator.saveToken();
            next();
            initState();
        } else if (other.isCharacterToken()){// el caraceter puede ser un token del tipo otros
            isPossibleToken();
            other.saveCharToken(String.valueOf(current()));
            next();
            initState();
        } else if (universal.isCharacterToken()){// el caracter puede ser un token del tipo universal
            isPossibleToken();
            universal.saveToken();
            next();
            initState();
        } else if (isSpace(current()) && possibleToken.getPossibleToken() != null){//termina la palabra y esta puede ser un token
            isPossibleToken();
            next();
            initState();
        } else if (isSpace(current())){// si el espacio no es token
            outputCode.add(String.valueOf(current()));
            next();
            initState();
        } else {//sigue avanzado
            //validar si se trata de una excepcion
            concat();
            isException();
        }
    }

    private void isException() throws LexicalAnalyzerException {
        if (other.isExceptionWord()){
            other.saveCharToken(possibleToken.getPossibleToken());
            possibleToken.reStart();
            initState();
        } else {
            isPossibleToken();
            next();
            initState();
        }
    }

    private void isPossibleToken() throws LexicalAnalyzerException {
        if (possibleToken.getPossibleToken() != null){
            if (tagOrType.isToken()){
                tagOrType.saveToken();
            } else if (ofClass.isToken('.')){
                ofClass.saveToken();
            } else if (ofClass.isToken('#')) {
                ofClass.saveToken();
            } else if (other.isToken()){
                other.saveCharToken(possibleToken.getPossibleToken());
            } else if (rule.isToken()) {
                rule.saveToken();
            } else if (rule.isKey()) {
                next();
                initState();
            }// VALIDAR IDENTIFICADORES
            //validar errores
        }
    }
}
