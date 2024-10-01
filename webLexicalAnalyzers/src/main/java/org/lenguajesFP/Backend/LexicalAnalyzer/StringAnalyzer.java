package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.List;

public class StringAnalyzer extends LexicalAnalyzer {

    protected LanguageTypeAnalyzer languageTypeAnalyzer;
    protected List<Token> tagToken;
    protected boolean approved = false;

    protected char startSymbol;
    protected char endSymnbol;

    public StringAnalyzer(LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken) {

        System.out.println("iniciando string analyzer");

        this.tagToken = tagToken;
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        super.initVars(this.languageTypeAnalyzer);
        startSymbol = '"';
        endSymnbol = '"';
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
                    "HTML",
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
        tagToken.add( new Token(
                possibleToken.getPossibleToken(),
                "cadena",
                "\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"\n",
                "HTML",
                index.getRow(),
                index.getColumn()
        ));
    }
}
