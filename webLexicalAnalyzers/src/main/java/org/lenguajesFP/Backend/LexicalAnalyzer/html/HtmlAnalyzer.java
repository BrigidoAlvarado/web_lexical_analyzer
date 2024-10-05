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

        System.out.println("iniciando a leer el posible codigo html");

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
            System.out.println("puede ser un cambio de estado");
            //guardo la salida de texto
            if (outputTag != null){
                outputCode.add(outputTag);
            }
            possibleToken.reStart();
            index.decrease();
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index, htmlTokens, cssTokens, jsTokens);
        } else {
            wordState();
        }
    }

    private void lessState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
        System.out.println("en less state");

            //el tokenn puede ser el nombre de la etiqueta

            System.out.println("puede ser el nombre de una etiqueta");

            tagTokens.add(new Token(possibleToken.getPossibleToken(),"Etiquetas_de_Apertura", possibleToken.getPossibleToken(), "HTML", index.getRow(), index.getColumn()));
            possibleToken.reStart();
            tagNameState();
    }

    private void tagNameState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
        TagName tagName = new TagName();
        System.out.println("en tagname state");
        //el nombre de la etiqueta es valido

        if (tagName.readTag(languageTypeAnalyzer)){
            System.out.println("el nombre de la etiqueta fue aprobada");
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

        System.out.println("evaluando la posible etiqueta: "+input[index.get()]);

        // Se puede tratar de una etiqueta de cierre

        if (input[index.get()] == '/'
        && !possibleToken.getPossibleToken().equalsIgnoreCase(HtmlTag.area.name())
        && !possibleToken.getPossibleToken().equalsIgnoreCase(HtmlTag.entrada.name())){
            next();
            if (input[index.get()] == '>' ){
                //System.out.println("es una etiqueta de cierre");
                //imprimir la traduccion
                outputTag += "/" + HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate() + ">";
                tagTokens.add(new Token( "/>", "Etiqueta cierre", "/>", "HTML", index.getRow(), index.getColumn()));
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

                System.out.println("tiene elementos validos");

                outputTag += htmlElement.getOutputElement();

                System.out.println("cogigo almacenado en la etiqueta "+htmlElement.getOutputElement());

                saveTag(); //se guarda la etiqueta
                next();
                textState();// se valida si tiene texto asociado
            }
        }catch (InvalidTokenException e){
            errorState(e.getError());
        }
    }

    private void textState() throws LexicalAnalyzerException {

        System.out.println("en el estado de texto");

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