package org.lenguajesFP.Backend.LexicalAnalyzer.html;

import org.lenguajesFP.Backend.LexicalAnalyzer.LanguageTypeAnalyzer;

public class OpeningTag extends HtmlAnalyzer{

    private String possibleTag;

    public void read(LanguageTypeAnalyzer languageTypeAnalyzer){
        super.initVars(languageTypeAnalyzer);
        initState();
    }

    private void initState(){

    }
}
