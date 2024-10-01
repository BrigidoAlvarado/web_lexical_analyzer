package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;

import java.util.List;

public class TextAnalizer extends StringAnalyzer{

    public TextAnalizer (LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken) {

        super(languageTypeAnalyzer, tagToken);
        System.out.println("inician el lecto de texto");
        startSymbol = '>';
        endSymnbol = '<';
    }

    @Override
    protected void initState() throws ArrayIndexOutOfBoundsException, InvalidTokenException{
        System.out.println("evaluando: "+input[index.get()]);
        if (input[index.get()] == startSymbol){
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

    @Override
    protected void stringState() throws ArrayIndexOutOfBoundsException{
        if (input[index.get()] == endSymnbol){
            finalState();
        } else {
            concat();
            next();
            stringState();
        }
    }
}
