package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.LexicalAnalyzer.LexicalAnalyzer;
import org.lenguajesFP.Backend.Token;

public class HtmlAnalyzer extends LexicalAnalyzer {

    private LanguageTypeAnalyzer languageTypeAnalyzer;

    protected String possibleTag;

    public void read(LanguageTypeAnalyzer typeAnalyzer){
        this.languageTypeAnalyzer = typeAnalyzer;
        super.initVars(typeAnalyzer);

        initState();
    }

    private void initState(){
        if (isSpace(input[index])){
            next();
            initState();
        } else if (input[index] == '<'){
            concat();
            next();
            lessState();
        }
    }

    private void lessState(){
        if (input[index] == '<'){
            index--;
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index);
        } else {
            tokens.add(new Token(possibleToken, "Etiqueta de apertura", row, column));
            OpeningTag openingTag = new OpeningTag();
            next();
            openingTag.read(languageTypeAnalyzer);
        }
    }
}
