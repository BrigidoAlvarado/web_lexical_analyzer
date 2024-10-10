package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.StringAnalyzer;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.enums.HtmlReservedWords;
import org.lenguajesFP.Backend.enums.HtmlTag;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.List;

public class HtmlElement extends HtmlAnalyzer {

    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private List<Token> tagToken;
    private boolean approved = false;
    private String outputCode;
    private String tagName;

    public HtmlElement(LanguageTypeAnalyzer languageTypeAnalyzer, List<Token> tagToken) throws InvalidTokenException, ArrayIndexOutOfBoundsException {
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        this.tagToken = tagToken;
        super.initVars(this.languageTypeAnalyzer);
        possibleToken.reStart();
    }

    public boolean readElements(String  tagName) throws InvalidTokenException, ArrayIndexOutOfBoundsException, LexicalAnalyzerException {
        this.tagName = tagName;
        initState();
        return approved;
    }

    private void initState() throws InvalidTokenException, ArrayIndexOutOfBoundsException, LexicalAnalyzerException {
        if (isSpace(input[index.get()])){//es un espacio
            next();
            initState();
        } else if (isLetter(input[index.get()])){//es una letra
            concat();
            next();
            elementState();
        } else{// es un error
            throw new InvalidTokenException(
                    new TokenError(possibleToken.getPossibleToken(),
                            null,
                            "HTML",
                            index.getRow(),
                            index.getColumn()));
        }
    }

    private void elementState() throws InvalidTokenException, ArrayIndexOutOfBoundsException, LexicalAnalyzerException {

        if (isLetter(input[index.get()])){//es una letra
            concat();
            next();
            elementState();
        } else if (isSpace(current())) {
            saveOnlyElement();
        } else if (input[index.get()] == '='){//es un signo igual
            saveElement();
        } else{// es un error
            throw new InvalidTokenException(new TokenError(
                    possibleToken.getPossibleToken(),
                    null,
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }

    private void saveElement() throws InvalidTokenException, ArrayIndexOutOfBoundsException, LexicalAnalyzerException {

        if (HtmlReservedWords.ID.isReservedWord(possibleToken.getPossibleToken())){
            //se crea el token de la palabra reservada
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
            possibleToken.reStart();
            // se guarda el token de =
            tagToken.add( new Token(
                    "=",
                    "Palbara Reservada",
                    "=",
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
            outputElement("=");
            next();
            //se empieza a leer la cadena asociada
            stringState();

        } else {// estado de error
            throw new InvalidTokenException( new TokenError(
                    possibleToken.getPossibleToken(),
                    null,
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }

    private void saveOnlyElement() throws InvalidTokenException, LexicalAnalyzerException {
        if (HtmlReservedWords.ID.isReservedWord(possibleToken.getPossibleToken())){
            //se crea el token de la palabra reservada
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
            possibleToken.reStart();
            // se guarda el token de =
            outputElement(" ");
            next();
            //se empieza a leer la cadena asociada
            otherElementsState();

        } else {// estado de error
            throw new InvalidTokenException( new TokenError(
                    possibleToken.getPossibleToken(),
                    null,
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }

    private void stringState() throws LexicalAnalyzerException, InvalidTokenException {

        StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer, tagToken,"HTML");
        if (stringAnalyzer.readString()){
            outputCode += possibleToken.getPossibleToken();
            possibleToken.reStart();
            next();
            otherElementsState();
        } else{
            throw new InvalidTokenException( new TokenError(
                    possibleToken.getPossibleToken(),
                    null,
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
        }
    }

    private void outputElement(String element) throws ArrayIndexOutOfBoundsException {
        if (outputCode == null){
            outputCode = element;
        } else {
            outputCode += element;
        }
    }

    private void otherElementsState() throws InvalidTokenException, ArrayIndexOutOfBoundsException, LexicalAnalyzerException {
        if (isSpace(input[index.get()])){// es un espacio
            outputCode = saveCurrentChar(outputCode);
            next();
            otherElementsState();
        } else if (isLetter(input[index.get()])){// puede ser una palabra reservada
            concat();
            next();
            elementState();
        } else if (input[index.get()] == '>') {// finaliza la cadena
            approved = true;
            tagToken.add( new Token(
                    ">",
                    "Etiquetas_de_Cierre",
                    ">",
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
            outputCode = saveCurrentChar(outputCode);
        } else if (current() == '/') {
            outputCode = saveCurrentChar(outputCode);
            next();
            if (current() == '>'){
                if ((HtmlTag.area.name().equalsIgnoreCase(tagName)||HtmlTag.entrada.name().equalsIgnoreCase(tagName))){
                    approved = true;
                    tagToken.add( new Token(
                            "/>",
                            "Etiquetas_de_Cierre",
                            "/>",
                            "HTML",
                            index.getRow(),
                            index.getColumn()
                    ));
                    outputCode = saveCurrentChar(outputCode);
                }
            } else {
                throw new InvalidTokenException(new TokenError(
                        possibleToken.getPossibleToken(),
                        null,
                        "HTML",
                        index.getRow(),
                        index.getColumn()
                ));
            }
        } else {
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
