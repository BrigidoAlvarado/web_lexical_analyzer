package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.enums.HtmlTag;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

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
            next();
            initState();
        } else if (input[index.get()] == '<'){
            outputTag = String.valueOf(input[index.get()]);
            concat();
            next();
            lessState();
        }
    }

    private void lessState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
        System.out.println("en less state");
        if (input[index.get()] == '<'){
            System.out.println("puede ser un cambio de estado");
            //el token puede ser un cambio de estado
            outputTag = null;
            possibleToken.reStart();
            index.decrease();
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index);
        } else {
            //el tokenn puede ser el nombre de la etiqueta
            System.out.println("puede ser el nombre de una etiqueta");
            tagTokens.add(new Token(possibleToken.getPossibleToken(),"Etiquetas_de_Apertura", possibleToken.getPossibleToken(), "HTML", index.getRow(), index.getColumn()));
            possibleToken.reStart();
            tagNameState();
        }
    }

    private void tagNameState() throws ArrayIndexOutOfBoundsException, LexicalAnalyzerException{
        TagName tagName = new TagName();
        System.out.println("en tagname state");
        //el nombre de la etiqueta es valido
        if (tagName.readTag(languageTypeAnalyzer)){
            System.out.println("el nombre de la etiqueta fue aprobada");
            tagTokens.add(new Token(possibleToken.getPossibleToken(), "Palabra Reservada", possibleToken.getPossibleToken(), "HTML", index.getRow(), index.getColumn()));
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
                possibleToken.reStart();

                System.out.println(outputTag);

                tagTokens.add(new Token(possibleToken.getPossibleToken(), "Etiqueta cierre", possibleToken.getPossibleToken(), "HTML", index.getRow(), index.getColumn()));
                possibleToken.reStart();
                saveTag();
            }
        }
        //Se trata de una etiqueta de apertura
        else if (isSpace(input[index.get()])){

            outputTag += HtmlTag.valueOf(possibleToken.getPossibleToken()).getTranslate();
            outputTag = saveCode(outputTag);
            //leer los posibles elementos de la etiqueta
            try {
                HtmlElement htmlElement = new HtmlElement(this.languageTypeAnalyzer, tagTokens);
                if (htmlElement.readElements()){

                    System.out.println("tiene elementos validos");

                    outputTag += htmlElement.getOutputElement();

                    System.out.println("cogigo almacenado en la etiqueta "+htmlElement.getOutputElement());

                    saveTag();
                    //validar si tiene texto asociado
                    //validar si la etiqueta de cierre concide con la de apertura
                    // reiniciar lectura
                }
            }catch (InvalidTokenException e){
                errorState(e.getError());
            }

        }
        // error
        else {
            errorState(null);
        }
    }

    private void saveTag(){
        tokens.addAll(tagTokens);
        outputCode.add(outputTag);
        tagTokens.clear();
        outputTag = null;
        System.out.println("texto de salida: " + outputCode);
    }


}
