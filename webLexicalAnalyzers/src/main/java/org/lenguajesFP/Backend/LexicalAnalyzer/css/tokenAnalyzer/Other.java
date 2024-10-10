package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

import java.util.List;

public class Other extends LexicalAnalyzer {

    public static final String ESPECIAL_TOKEN = "'[A-Za-z]'";
    public static final String TYPE = "Otros";

    public static final List<String> OTHERS = List.of("::after","::before",":active",":hover","px","rem","em","vw","vh","odd","even");

    public static final List<String> EXCEPTION_TOKENS = List.of(":not()", ":nth-child()");

    public static final String EXCEPTION_WORD = ":n";

    public static final List<Character> OTHER_CHARS = List.of('%',';',',','(',')','{','}',':');

    public static final Character CHAR_EXCEPTIONS = ':';

    private boolean isToken = false;
    private  final Combinator combinator;
    private final Universal universal;

    public Other(LexicalAnalyzer lexicalAnalyzer) {
        initVars(lexicalAnalyzer);
        combinator = new Combinator(lexicalAnalyzer);
        universal = new Universal(lexicalAnalyzer);
    }

    public boolean isCharacterToken(){
        if (OTHER_CHARS.contains(current())){
            if (current() == CHAR_EXCEPTIONS){
                return isOtherExceptions();
            }
            return true;
        }
        return false;
    }

    public void saveToken(String token){
        tokens.add(new Token(
                token,
                TYPE,
                token,
                "CSS",
                index.getRow(),
                index.getColumn()
        ));

    }

    private boolean isOtherExceptions(){
        return (isSpace(input[index.get() + 1]));
    }

    public boolean isExceptionWord() {
        isToken = false;
        if (possibleToken.getPossibleToken().equals(EXCEPTION_WORD)){
            next();
            readException();
        }
        return isToken;
    }

    public boolean isToken(){
        return OTHERS.contains(possibleToken.getPossibleToken());
    }

    private void readException(){
        if ((isSpace(current())
                || isCharacterToken()
                || combinator.isCharacterToken()
                || universal.isCharacterToken())
                && current() != '(' && current() != ')'
        ){
            isToken = EXCEPTION_TOKENS.contains(possibleToken.getPossibleToken());
        } else {
            concat();
            next();
            readException();
        }
    }
}
