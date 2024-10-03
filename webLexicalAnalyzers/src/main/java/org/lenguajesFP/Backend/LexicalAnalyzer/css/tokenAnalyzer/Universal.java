package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class Universal extends LexicalAnalyzer {

    private static final char TOKEN = '*';
    public static final String TYPE = "Universal";

    public Universal(LexicalAnalyzer lexicalAnalyzer) {
        super.initVars(lexicalAnalyzer);
    }

    public boolean isToken(){
        return current() == TOKEN;
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
}
