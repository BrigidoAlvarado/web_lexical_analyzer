package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.enums.HtmlReservedWords;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;

import java.util.List;

public class HtmlElement extends HtmlAnalyzer {

    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private List<Token> tagToken;
    private boolean approved = false;
    private String outputCode;

    public HtmlElement(LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken) throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        System.out.println("creando e inicializando los valores para el lector de elementos");
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        this.tagToken = tagToken;
        super.initVars(this.languageTypeAnalyzer);
        possibleToken.reStart();
    }

    public boolean readElements() throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        System.out.println("iniciando");
        initState();
        return approved;
    }

    private void initState() throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        System.out.println("en el estado inicial del lector de elementos");
        if (isSpace(input[index.get()])){
            next();
            initState();
        } else if (isLetter(input[index.get()])){
            concat();
            next();
            elementState();
        } else{
            throw new InvalidTokenException(
                    new TokenError(possibleToken.getPossibleToken(),
                            null,
                            "HTML",
                            index.getRow(),
                            index.getColumn()));
        }
    }

    private void elementState() throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        System.out.println("en el estado de is Letter");
        if (isLetter(input[index.get()])){
            concat();
            next();
            elementState();
        } else if (input[index.get()] == '='){
            saveElement();
        } else{
            throw new InvalidTokenException(new TokenError(
                    possibleToken.getPossibleToken(),
                    null,
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }

    private void saveElement() throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        System.out.println("en el estado guardando el elemento");
        System.out.println("el posible elemento es "+ possibleToken.getPossibleToken());

        if (HtmlReservedWords.ID.isReservedWord(possibleToken.getPossibleToken())){
            approved = true;
            //se crea el token
            tagToken.add(
                    new Token(possibleToken.getPossibleToken(),
                            "Palabra Reservada",
                            possibleToken.getPossibleToken(),
                            "HTML",
                            index.getRow(),
                            index.getColumn())
            );
            //se guarda la salida de texto
            outputElement(possibleToken.getPossibleToken());
            System.out.println("elemento guardado y leido "+possibleToken.getPossibleToken());
            possibleToken.reStart();
            tagToken.add( new Token(
                    "=",
                    "Palbara Reservada",
                    "=",
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
            outputElement("=");
            System.out.println("se imprimira: "+outputCode);
            next();
            stringState();


        } else {
            throw new InvalidTokenException( new TokenError(
                    possibleToken.getPossibleToken(),
                    null,
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }

    private void stringState(){
        System.out.println("en el estado valida cadenas");
    }

    private void outputElement(String element) throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        if (outputCode == null){
            outputCode = element;
        } else {
            outputCode += element;
        }
    }

    private void otherElementsState() throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        if (isSpace(input[index.get()])){
            next();
            otherElementsState();
        } else if (isLetter(input[index.get()])){
            concat();
            next();
            elementState();
        } else if (input[index.get()] == '>') {

        }else {
                throw new InvalidTokenException(new TokenError(
                        possibleToken.getPossibleToken(),
                        null,
                        "HTML",
                        index.getRow(),
                        index.getColumn()
                ));
        }
    }

    public String getOutputElement() {
        return outputCode;
    }
}
