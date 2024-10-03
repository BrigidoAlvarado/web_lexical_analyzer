package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

import java.util.List;

public class Other extends LexicalAnalyzer {

    public static final String ESPECIAL_TOKEN = "'[A-Za-z]'";
    public static final String TYPE = "Otros";
    //public static final String

    public static final List<String> OTHERS = List.of("px","rem","em","vw","vh","odd","even");

    public static final List<String> OTHERS_EXCEPTIONS = List.of(":hover",":active",":not()","::before","::after");

    public static final List<Character> OTHER_CHARS = List.of('%',';',',','(',')','{','}');

    public static final Character CHAR_EXCEPCIONS = ':';

    private boolean isToken = false;

    public Other(LexicalAnalyzer lexicalAnalyzer) {
        initVars(lexicalAnalyzer);
    }

    public boolean isToken(){
        if (OTHER_CHARS.contains(current())){
            isToken = true;
        } else if (current() == CHAR_EXCEPCIONS){
            isToken = isOtherExceptions();
        }
        return isToken;
    }

    public void saveToken(){
        tokens.add(new Token(
                String.valueOf(current()),
                TYPE,
                String.valueOf(current()),
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(String.valueOf(current()));
    }

    private boolean isOtherExceptions(){
        return (isSpace(input[index.get() + 1]));
    }
}
