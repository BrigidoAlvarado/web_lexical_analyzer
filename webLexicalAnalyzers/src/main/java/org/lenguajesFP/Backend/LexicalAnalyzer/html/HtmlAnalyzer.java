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
    private String nameTag;

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
            nameTag = possibleToken.getPossibleToken();
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
            errors.add(new TokenError(possibleToken.getPossibleToken(), null, "HTML", index.getRow(), index.getColumn()));
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

        if (isSpace(input[index.get()]) || input[index.get()] == '<'){
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
                if (possibleToken.getPossibleToken().equalsIgnoreCase(HtmlTag.area.name())
                || possibleToken.getPossibleToken().equalsIgnoreCase(HtmlTag.entrada.name())){
                    outputTag += HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate() + "/>";
                    tagTokens.add(new Token( "/>", "Etiqueta cierre", "/>", "HTML", index.getRow(), index.getColumn()));
                } else{
                    outputTag += "/" + HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate() + ">";
                    tagTokens.add(new Token( "/>", "Etiqueta cierre", "/>", "HTML", index.getRow(), index.getColumn()));                }
                possibleToken.reStart();
                saveTag();
                next();
                initState();
            }
        }
        //Se trata de una etiqueta de apertura
        else if (isSpace(input[index.get()]) || input[index.get()] == '>'){
            outputTag += HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate();
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
            if (htmlElement.readElements(nameTag)){
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
                index.decrease();
            }
            if (textAnalizer.readString()) {
                    outputTag = possibleToken.getPossibleToken();
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
    }

}