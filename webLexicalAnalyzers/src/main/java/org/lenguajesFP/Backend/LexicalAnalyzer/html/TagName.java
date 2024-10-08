package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.enums.HtmlTag;

public class TagName extends HtmlAnalyzer{

    private boolean approved = false;

    public boolean readTag(LanguageTypeAnalyzer languageTypeAnalyzer) throws ArrayIndexOutOfBoundsException {
        super.initVars(languageTypeAnalyzer);
        initState();
        System.out.println("retornando "+approved);
        return approved;
    }

    private void initState() throws ArrayIndexOutOfBoundsException{
        if (isSpace(input[index.get()])){
            next();
            initState();
        } else if(isLessLetter(input[index.get()])){
            concat();
            next();
            tagNameState();
        } else{
            approved = false;
        }
    }

    private void tagNameState() throws ArrayIndexOutOfBoundsException{
        if (isSpace(input[index.get()]) || input[index.get()] == '/' || input[index.get()] == '>'){
            //validar si es una palabra reservada
            approved = isReservedWord();
            System.out.println("evaluando "+possibleToken.getPossibleToken());
            System.out.println("la palabra reservada es: " + approved);
        } else if( isLessLetterOrNumber(input[index.get()])){
          concat();
          next();
          tagNameState();
        } else{
            approved = false;
        }
    }

    private boolean isReservedWord() throws ArrayIndexOutOfBoundsException{
        try {
            HtmlTag.valueOf(possibleToken.getPossibleToken());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
