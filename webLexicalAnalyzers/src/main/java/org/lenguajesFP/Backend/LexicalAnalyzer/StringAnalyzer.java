package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.List;

public class StringAnalyzer extends LexicalAnalyzer {

    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private List<Token> tagToken;
    private boolean approved = false;

    public StringAnalyzer(LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken) {
        System.out.println("iniciando string analyzer");
        this.tagToken = tagToken;
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        super.initVars(this.languageTypeAnalyzer);
    }

    public boolean readString() throws LexicalAnalyzerException {
        initState();
        return approved;
    }

    private void initState() throws ArrayIndexOutOfBoundsException{
        if (input[index.get()] == '"'){
            concat();
            finalState();
        } else {
            concat();
            next();
            stringState();
        }
    }

    private void stringState() throws ArrayIndexOutOfBoundsException{
        if (input[index.get()] == '"'){
            concat();
            finalState();
        } else {
            concat();
            next();
            stringState();
        }
    }

    private void finalState() throws ArrayIndexOutOfBoundsException{
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
