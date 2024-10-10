package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.StringAnalyzer;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.List;

public class TextAnalizer extends StringAnalyzer {

    public TextAnalizer (LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken, String languague) throws LexicalAnalyzerException {

        super(languageTypeAnalyzer, tagToken, languague);
        startSymbol = '>';
        endSymbol = '<';
    }

    @Override
    protected void initState() throws ArrayIndexOutOfBoundsException, InvalidTokenException{
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
        if (input[index.get()] == endSymbol){
            finalState();
        } else {
            concat();
            next();
            stringState();
        }
    }
}
