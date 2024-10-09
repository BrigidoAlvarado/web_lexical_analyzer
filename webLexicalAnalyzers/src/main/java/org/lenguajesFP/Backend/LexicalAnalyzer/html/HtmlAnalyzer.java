package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.*;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.enums.HtmlTag;
import org.lenguajesFP.Backend.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class HtmlAnalyzer extends LexicalAnalyzer {

    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private List<Token> tagTokens = new ArrayList<>();
    private String outputTag;

    public void read(LanguageTypeAnalyzer typeAnalyzer) throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
        this.languageTypeAnalyzer = typeAnalyzer;
        super.initVars(typeAnalyzer);
        initState();
    }

    private void initState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{

        if (isSpace(input[index.get()])){
            outputTag = saveCurrentChar(outputTag);
            next();
            initState();
        } else if (input[index.get()] == '<'){
            outputTag = saveCurrentChar(outputTag);
            concat();
            next();
            lessState();
        } else  if (input[index.get()] == '>'){
            //guardo la salida de texto
            if (outputTag != null){
                outputCode.add(outputTag);
            }
            possibleToken.reStart();
            index.decrease();
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index, htmlTokens, cssTokens, jsTokens);
        } else if (input[index.get()] == '/'){//cometario
            if (outputTag != null){outputCode.add(outputTag); outputTag = null;}
            CommentAnalyzer commentAnalyzer = new CommentAnalyzer(languageTypeAnalyzer);
            commentAnalyzer.readComment("HTML");
            initState();
        } else {
            wordState();
        }
    }

    private void lessState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{

            tagTokens.add(new Token(possibleToken.getPossibleToken(),"Etiquetas_de_Apertura", possibleToken.getPossibleToken(), "HTML", index.getRow(), index.getColumn()));
            possibleToken.reStart();
            tagNameState();
    }

    private void tagNameState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
        TagName tagName = new TagName();
        if (tagName.readTag(languageTypeAnalyzer)){
            tagTokens.add(new Token(
                    possibleToken.getPossibleToken(),
                    "Palabra Reservada",
                    possibleToken.getPossibleToken(),
                    "HTML",
                    index.getRow(),
                    index.getColumn()));
            typeTagState();
        } else{
            errorState(null);
        }
    }

    private void errorState(TokenError error) throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{

        if (error == null){
            errors.add(new TokenError(possibleToken.getPossibleToken(), "", "HTML", row, column));
        } else {
            errors.add(error);
        }
        possibleToken.reStart();
        tagTokens.clear();
        outputTag = null;
        //omite la lecutura de toda la linea
        while(input[index.get()] != '\n'){
            next();
        }
        //regresa al estado inicial
        initState();
    }

    private void wordState() throws LexicalAnalyzerException{

        System.out.println("en el estado de palabras");

        if (isSpace(input[index.get()]) || input[index.get()] == '<' || input[index.get()] == '<'){
            errorState(null);
        } else {
            concat();
            next();
            wordState();
        }
    }

    private void typeTagState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException {

        // Se puede tratar de una etiqueta de cierre
        if (input[index.get()] == '/'){
            next();
            if (input[index.get()] == '>' ){
                System.out.println("guardando la etiqueta: "+possibleToken.getPossibleToken());
                if (possibleToken.getPossibleToken().equalsIgnoreCase(HtmlTag.area.name())
                || possibleToken.getPossibleToken().equalsIgnoreCase(HtmlTag.entrada.name())){
                    outputTag += HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate() + "/>";
                    tagTokens.add(new Token( "/>", "Etiqueta cierre", "/>", "HTML", index.getRow(), index.getColumn()));
                } else{
                    outputTag += "/" + HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate() + ">";
                    tagTokens.add(new Token( "/>", "Etiqueta cierre", "/>", "HTML", index.getRow(), index.getColumn()));                }
                //System.out.println("es una etiqueta de cierre");
                //imprimir la traduccion
                possibleToken.reStart();
                saveTag();
                next();
                initState();
            }
        }
        //Se trata de una etiqueta de apertura
        else if (isSpace(input[index.get()]) || input[index.get()] == '>'){
            outputTag += HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate();
            System.out.println("salida antes de entrar al estado final "+outputTag);
            endTagState();
        }
        // error
        else {
            errorState(null);
        }
    }

    private void endTagState() throws LexicalAnalyzerException{

        //valido el siguiente espacio
        if (isSpace(input[index.get()])){
            outputTag = saveCurrentChar(outputTag);
            next();
            endTagState();
        } else if (input[index.get()] == '>'){
            outputTag = saveCurrentChar(outputTag);
            tagTokens.add( new Token(
                    ">",
                    "Etiquetas_de_Cierre",
                    ">",
                    "HTML",
                    index.getRow(),
                    index.getColumn()
            ));
            saveTag();
            next();
            textState();
        } else{
            elementsState();
        }
    }

    private void elementsState() throws LexicalAnalyzerException{
        //leer los posibles elementos de la etiqueta
        try {
            HtmlElement htmlElement = new HtmlElement(this.languageTypeAnalyzer, tagTokens);
            if (htmlElement.readElements()){
                outputTag += htmlElement.getOutputElement();
                saveTag(); //se guarda la etiqueta
                next();
                textState();// se valida si tiene texto asociado
            }
        }catch (InvalidTokenException e){
            errorState(e.getError());
        }
    }

    private void textState() throws LexicalAnalyzerException {
        TextAnalizer textAnalizer = new TextAnalizer(languageTypeAnalyzer, tagTokens,"HTML");

        try {
            if (isSpace(input[index.get()]) || input[index.get()] == '<') {

                if (isSpace(input[index.get()])){
                    outputCode.add(String.valueOf(input[index.get()]));
                    next();
                }
                initState();
            } else {
                System.out.println("no es un espacio en blanco");
                index.decrease();
            }
            if (textAnalizer.readString()) {
                System.out.println("tiene texto asociado");

                    System.out.println("se aprobo el texto asociado");

                    outputTag = possibleToken.getPossibleToken();

                    System.out.println("se guardo el token texto " + possibleToken.getPossibleToken());
                    System.out.println("el texto leido impreso es:  " + outputTag);

                    possibleToken.reStart();
                    saveTag();
                    initState();
            } else {
                concat();
                errorState(null);
            }
        } catch (InvalidTokenException e){
            errorState(e.getError());
        }
    }

    private void saveTag(){
        tokens.addAll(tagTokens);
        outputCode.add(outputTag);
        tagTokens.clear();
        outputTag = null;

        possibleToken.reStart();
        System.out.println("se guardo la etiqueta");
        System.out.println("texto de salida de la etiqueta recien guardada: " + outputCode);
    }

}