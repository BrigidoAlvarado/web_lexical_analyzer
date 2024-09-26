package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.enums.LexicalState;
import java.util.List;

import static org.lenguajesFP.Backend.enums.LexicalState.html;

public class LanguageTypeAnalyzer extends LexicalAnalyzer{

    public void read(List<Token> tokens, List<Token> errors, List<String> outputCode,
                     char[] input, int index){
        this.tokens = tokens;
        this.errors = errors;
        this.outputCode = outputCode;
        this.input = input;
        this.index = index;
        initState();

    }

    private void initState(){
        try {
            if (isSpace(input[index])){
                next();
                initState();
            } else{
                concat();
                next();
                wordState();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    private void wordState(){
        try {
            if (isSpace(input[index])){
                redirect();
            } else{
                concat();
                next();
                wordState();
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    private void redirect(){

        try {
            if (html.isLexicalState(possibleToken)){
                tokens.add(new Token(possibleToken,"Token de estado",row, column));
                possibleToken = null;
                //redirect
            } else if (LexicalState.css.isLexicalState(possibleToken)){
                tokens.add(new Token(possibleToken,"Token de estado",row, column));
                possibleToken = null;
                System.out.println("se encontro un "+ possibleToken);
                System.out.println("redirigiendo al analizador de html");
                //redirect
            } else if (LexicalState.js.isLexicalState(possibleToken)){
                tokens.add(new Token(possibleToken,"Token de estado",row, column));
                possibleToken = null;
                //redirect
            } else{
                errors.add(new TokenError(possibleToken, "Analizador de estados", row, column));
                next();
                System.out.println("se encontro un error llamado"+ possibleToken);
                System.out.println("redirigiendo al inicio");
                initState();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
