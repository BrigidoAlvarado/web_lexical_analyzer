package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;

import java.util.ArrayList;
import java.util.List;

public class HtmlAnalyzer extends LexicalAnalyzer {

    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private List<Token> tagTokens = new ArrayList<>();
    private String outputTag;

    public void read(LanguageTypeAnalyzer typeAnalyzer) throws ArrayIndexOutOfBoundsException{
        this.languageTypeAnalyzer = typeAnalyzer;
        super.initVars(typeAnalyzer);
        initState();
    }

    private void initState() throws ArrayIndexOutOfBoundsException{
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

    private void lessState() throws ArrayIndexOutOfBoundsException{
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

    private void tagNameState() throws ArrayIndexOutOfBoundsException{
        TagName tagName = new TagName();
        System.out.println("en tagname state");
        //el nombre de la etiqueta es valido
        if (tagName.readTag(languageTypeAnalyzer)){
            System.out.println("la palabra reservado fue aprobada");
            tagTokens.add(new Token(possibleToken.getPossibleToken(), "Palabra Reservada", possibleToken.getPossibleToken(), "HTML", index.getRow(), index.getColumn()));
            outputTag += possibleToken.getPossibleToken();
            possibleToken.reStart();
            typeTagState();
        } else{
            errorState();
        }
    }

    private void errorState() throws ArrayIndexOutOfBoundsException{
        errors.add(new TokenError(possibleToken.getPossibleToken(), "", "HTML", row, column));
        possibleToken.reStart();
        tagTokens.clear();
        //avanza hasta toda la linea
        while(input[index.get()] != '\n'){
            next();
        }
        //regresa al estado inicial
        initState();
    }

    private void typeTagState() throws ArrayIndexOutOfBoundsException{
        System.out.println("evaluando "+input[index.get()]);
        if (input[index.get()] == '/'){
            concat();
            next();
            if (input[index.get()] == '>'){
                System.out.println("es una etiqueta de cierre");
                concat();
                outputTag += possibleToken.getPossibleToken();
                System.out.println(outputTag);
                tagTokens.add(new Token(possibleToken.getPossibleToken(), "Etiqueta cierre", possibleToken.getPossibleToken(), "HTML", index.getRow(), index.getColumn()));
                possibleToken.reStart();
                saveTag();
            }
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
