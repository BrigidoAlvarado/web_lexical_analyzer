package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

import java.util.List;

public class Other extends LexicalAnalyzer {

    public static final String ESPECIAL_TOKEN = "'[A-Za-z]'";
    public static final String TYPE = "Otros";
    //public static final String

    public static final List<String> OTHERS = List.of("::after","::before",":active",":hover","px","rem","em","vw","vh","odd","even");

    public static final String EXCEPTION_TOKEN = ":not()";

    public static final String EXCEPTION_WORD = ":not";

    public static final List<Character> OTHER_CHARS = List.of('%',';',',','(',')','{','}');

    public static final Character CHAR_EXCEPTIONS = ':';

    private boolean isToken = false;
    private Combinator combinator;
    private Universal universal;

    public Other(LexicalAnalyzer lexicalAnalyzer) {
        initVars(lexicalAnalyzer);
        combinator = new Combinator(lexicalAnalyzer);
        universal = new Universal(lexicalAnalyzer);
    }

    public boolean isCharacterToken(){
        if (OTHER_CHARS.contains(current())){
            isToken = true;
        } else if (current() == CHAR_EXCEPTIONS){
            isToken = isOtherExceptions();
        }
        return isToken;
    }

    public void saveCharToken(String token){
        tokens.add(new Token(
                token,
                TYPE,
                token,
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(String.valueOf(current()));
    }

    private boolean isOtherExceptions(){
        return (isSpace(input[index.get() + 1]));
    }

    public boolean isExceptionWord() {
        boolean isToken = false;
        if (possibleToken.getPossibleToken().equalsIgnoreCase(EXCEPTION_WORD)) {

            if (
                    combinator.isCharacterToken() ||
                    universal.isCharacterToken() || isSpace(current()) ||
                    (this.isCharacterToken() && current() != '(' && current() != ')'))
            {
                    isToken = (possibleToken.getPossibleToken().equalsIgnoreCase(EXCEPTION_TOKEN));

            }  else {
                concat();
                next();
                isExceptionWord();
            }
        }
        return isToken;
    }

    public boolean isToken(){
        return OTHERS.contains(possibleToken.getPossibleToken());
    }
}
