package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Index;
import org.lenguajesFP.Backend.LexicalAnalyzer.css.CssAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.html.HtmlAnalyzer;
import org.lenguajesFP.Backend.PossibleToken;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.enums.LexicalState;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.List;
import java.util.stream.Collectors;

import static org.lenguajesFP.Backend.enums.LexicalState.html;

public class LanguageTypeAnalyzer extends LexicalAnalyzer{

    public List<String> read(List<Token> tokens, List<Token> errors, List<String> outputCode,
                     char[] input, Index index, List<String> htmlOutput, List<String> cssOutput,
                             List<String> jsOutput) throws LexicalAnalyzerException{
        this.tokens = tokens;
        this.errors = errors;
        this.outputCode = outputCode;
        this.input = input;
        this.index = index;
        this.possibleToken = new PossibleToken();
        this.htmlTokens = htmlOutput;
        this.cssTokens = cssOutput;
        System.out.println("2 cssTokens: " + cssTokens);
        this.jsTokens = jsOutput;

        try {
            initState();
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("SE ACABO XDD");
        }

        System.out.println("Escritura html");
        String finalHtml = htmlTokens.stream().collect(Collectors.joining());
        System.out.println(finalHtml);
        System.out.println("Escritura CSS");
        String finalCss = cssTokens.stream().collect(Collectors.joining());
        System.out.println(finalCss);
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("Token hallados:");
        for (Token token : tokens) {
            System.out.println(token.getLexeme() + " " + token.getType());
        }
        System.out.println("errores hallados");
        for (Token token : errors) {
            System.out.println(token);
        }
        return outputCode;

    }

    private void initState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
            if (isSpace(input[index.get()])){
                next();
                initState();
            } else{
                concat();
                next();
                wordState();
            }
    }

    private void wordState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
            if (isSpace(input[index.get()])){
                redirect();
            } else{
                concat();
                next();
                wordState();
            }
    }

    private void redirect() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException {

            if (html.isLexicalState(possibleToken.getPossibleToken())){
                tokens.add(new Token(possibleToken.getPossibleToken(),"Estado", possibleToken.getPossibleToken(), "" , index.getRow(), index.getColumn()));
                System.out.println("se encontro un "+ possibleToken);
                System.out.println("redirigiendo al analizador de html");
                possibleToken.reStart();
                HtmlAnalyzer htmlAnalyzer = new HtmlAnalyzer();
                outputCode = htmlTokens;
                htmlAnalyzer.read(this);

            } else if (LexicalState.css.isLexicalState(possibleToken.getPossibleToken())){
                tokens.add(new Token(possibleToken.getPossibleToken(),"Estado", possibleToken.getPossibleToken(), "", index.getRow(), index.getColumn()));
                possibleToken.reStart();
                System.out.println("redirigiendo al analizador de codigo css");
                CssAnalyzer cssAnalyzer = new CssAnalyzer();
                System.out.println("css tokens es: "+cssTokens);
                outputCode = cssTokens;
                cssAnalyzer.readCss(this);
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
