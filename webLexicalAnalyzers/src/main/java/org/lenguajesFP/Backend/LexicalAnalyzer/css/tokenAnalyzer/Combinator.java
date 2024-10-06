package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combinator extends LexicalAnalyzer {

    public final static List<Character> COMBINATOR = new ArrayList<>(Arrays.asList(
            '>',
            '+',
            '~',
            ' '
    ));

    private boolean isToken;

    public Combinator(LexicalAnalyzer languageTypeAnalyzer) {
        initVars(languageTypeAnalyzer);
    }

    public boolean isCharacterToken() {
        isToken = false;
        System.out.println("evaluando: " + current() );
        if (COMBINATOR.contains(current())) {
            System.out.println("contenido en el arreglo");
            isToken = true;
            if (current() == ' '){
                isToken = validateSpace();
                System.out.println("token espacio; " + isToken);
            }
        }
        return isToken;
    }

    private boolean validateSpace(){
        return (input[index.get()-1] != ' ' && input[index.get()+1] != ' ');
    }

    public void saveToken(){
        tokens.add( new Token(
                String.valueOf(current()),
                "Combinadores",
                String.valueOf(current()),
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        outputCode.add(String.valueOf(current()));
    }
}
