package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;
import org.lenguajesFP.Backend.enums.HtmlTag;

public class TagName extends HtmlAnalyzer{

    private boolean approved = false;

    public boolean readTag(LanguageTypeAnalyzer languageTypeAnalyzer) throws ArrayIndexOutOfBoundsException {
        super.initVars(languageTypeAnalyzer);
        initState();
        return approved;
    }

    private void initState() throws ArrayIndexOutOfBoundsException{
        System.out.println("en el estado inicial de tag name "+input[index.get()]);
        if (isSpace(input[index.get()])){
            next();
            initState();
        } else if(isLessLetter(input[index.get()])){
            System.out.println("es una letra");
            concat();
            next();
            wordState();
        } else{
            approved = false;
        }
    }

    private void wordState() throws ArrayIndexOutOfBoundsException{
        if (isSpace(input[index.get()]) || input[index.get()] == '/'){
            //validar si es una palabra reservada
            approved = isReservedWord();
            System.out.println("la palabra reservada es: " + approved);
        } else if( isLessLetter(input[index.get()])){
            System.out.println("es una letra otra vez");
          concat();
          next();
          wordState();
        } else{
            approved = false;
        }
    }

    private boolean isReservedWord() throws ArrayIndexOutOfBoundsException{
        try {
            System.out.println("la posible palabra reservada es: " + possibleToken.getPossibleToken());
            HtmlTag tag = HtmlTag.valueOf(possibleToken.getPossibleToken());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
