package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Index;
import org.lenguajesFP.Backend.LexicalAnalyzer.html.HtmlAnalyzer;
import org.lenguajesFP.Backend.PossibleToken;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.enums.LexicalState;

import java.util.List;

import static org.lenguajesFP.Backend.enums.LexicalState.html;

public class LanguageTypeAnalyzer extends LexicalAnalyzer{

    public List<String> read(List<Token> tokens, List<Token> errors, List<String> outputCode,
                     char[] input, Index index){
        this.tokens = tokens;
        this.errors = errors;
        this.outputCode = outputCode;
        this.input = input;
        this.index = index;
        this.possibleToken = new PossibleToken();
        try {
            initState();
        }catch (ArrayIndexOutOfBoundsException e){

        }
        System.out.println("retornando "+outputCode);
        System.out.println("tokens hallados");
        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println("errores hallados");
        for (Token token : errors) {
            System.out.println(token);
        }
        return outputCode;

    }

    private void initState() throws ArrayIndexOutOfBoundsException{
        try {
            if (isSpace(input[index.get()])){
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

    private void wordState() throws ArrayIndexOutOfBoundsException{
            if (isSpace(input[index.get()])){
                redirect();
            } else{
                concat();
                next();
                wordState();
            }
    }

    private void redirect() throws ArrayIndexOutOfBoundsException{

            if (html.isLexicalState(possibleToken.getPossibleToken())){
                tokens.add(new Token(possibleToken.getPossibleToken(),"Estado", possibleToken.getPossibleToken(), "" , index.getRow(), index.getColumn()));
                System.out.println("se encontro un "+ possibleToken);
                System.out.println("redirigiendo al analizador de html");
                possibleToken.reStart();
                HtmlAnalyzer htmlAnalyzer = new HtmlAnalyzer();
                htmlAnalyzer.read(this);

            } else if (LexicalState.css.isLexicalState(possibleToken.getPossibleToken())){
                tokens.add(new Token(possibleToken.getPossibleToken(),"Estado", possibleToken.getPossibleToken(), "", index.getRow(), index.getColumn()));
                possibleToken.reStart();
                //redirect
            } else if (LexicalState.js.isLexicalState(possibleToken.getPossibleToken())){
                tokens.add(new Token(possibleToken.getPossibleToken(),"Estado", possibleToken.getPossibleToken(),"", index.getRow(), index.getColumn()));
                possibleToken.reStart();
                //redirect
            } else{
                errors.add(new TokenError(possibleToken.getPossibleToken(), "Analizador de estados","", index.getRow(), index.getColumn()));
                next();
                System.out.println("se encontro un error llamado "+ possibleToken);
                System.out.println("redirigiendo al inicio");
                for (Token token : errors){
                    System.out.println(token.getLexeme());
                }
                possibleToken.reStart();
                initState();
            }
    }
}
