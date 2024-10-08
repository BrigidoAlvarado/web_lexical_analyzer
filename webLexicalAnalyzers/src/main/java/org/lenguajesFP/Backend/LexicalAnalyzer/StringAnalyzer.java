package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer.Other;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.List;

public class StringAnalyzer extends LexicalAnalyzer {

    protected LanguageTypeAnalyzer languageTypeAnalyzer;
    protected List<Token> tagToken;
    protected String language;
    protected boolean approved = false;
    protected char startSymbol;
    protected char endSymnbol;

    public StringAnalyzer(LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken, String language) throws LexicalAnalyzerException {
        this.language = language;
        this.tagToken = tagToken;
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        super.initVars(this.languageTypeAnalyzer);
        startSymbol = '"';
        endSymnbol = '"';
    }

    public StringAnalyzer(LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken, String language, char startSymbol, char endSymbol) throws LexicalAnalyzerException {
        this.language = language;
        this.tagToken = tagToken;
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        super.initVars(this.languageTypeAnalyzer);
        this.startSymbol = startSymbol;
        this.endSymnbol = endSymbol;
    }


    public boolean readString() throws LexicalAnalyzerException, InvalidTokenException {
        System.out.println("entrando en el estado inicial");
        initState();
        return approved;
    }

    protected void initState() throws ArrayIndexOutOfBoundsException, InvalidTokenException{
        System.out.println("evaluando: "+input[index.get()]);
        if (input[index.get()] == startSymbol){
            concat();
            next();
            stringState();
        } else {
            concat();
            throw new InvalidTokenException( new TokenError(
                    possibleToken.getPossibleToken(),
                    null,
                    language,
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }

    protected void stringState() throws ArrayIndexOutOfBoundsException{
        if (input[index.get()] == endSymnbol){
            concat();
            finalState();
        } else {
            concat();
            next();
            stringState();
        }
    }

    protected void finalState() throws ArrayIndexOutOfBoundsException{
        approved = true;
        if (possibleToken.getPossibleToken().equals(Other.ESPECIAL_TOKEN)){
            tagToken.add( new Token(
                    possibleToken.getPossibleToken(),
                    "Otros",
                    possibleToken.getPossibleToken(),
                    "CSS",
                    index.getRow(),
                    index.getColumn()
            ));
        } else {
            tagToken.add( new Token(
                    possibleToken.getPossibleToken(),
                    "cadena",
                    "\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"\n",
                    language,
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }
}
