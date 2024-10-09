package org.lenguajesFP.Backend.LexicalAnalyzer;

import org.lenguajesFP.Backend.Token;
import org.lenguajesFP.Backend.TokenError;

public class CommentAnalyzer extends LexicalAnalyzer{

    private String output;
    private String language;
    public static final char COMMENT_SYMBOL = '/';
    public static final String REGULAR_EXPRESSION = "// [a-zA-Z]|[0-9]|[.]";

    public CommentAnalyzer(LanguageTypeAnalyzer languageTypeAnalyzer) {
        super.initVars(languageTypeAnalyzer);
    }

    public void readComment(String language){
        this.language = language;
        if (input[index.get()] == COMMENT_SYMBOL) {
                output = saveCurrentChar(output);
                concat();
                next();
                commentSymbol();
        } else{
            concat();
            next();
            errorState();
        }
    }

    private void commentSymbol(){
        if (input[index.get()] == COMMENT_SYMBOL) {
            output = saveCurrentChar(output);
            concat();
            commentContent();
        }
    }

    private void commentContent(){
        if (input[index.get() + 1] != '\n'){
            next();
            output = saveCurrentChar(output);
            concat();
            commentContent();
        } else{
            saveToken();
            next();
        }
    }

    private void saveToken(){
        tokens.add(new Token(
                possibleToken.getPossibleToken(),
                "Comentario",
                REGULAR_EXPRESSION,
                language,
                index.getRow(),
                index.getColumn()
        ));
        possibleToken.reStart();
        outputCode.add(output);
    }

    private void errorState(){
        if (isSpace(input[index.get()])) {
            saveErrorToken();
        } else{
            concat();
            next();
            errorState();
        }
    }

    private void saveErrorToken(){
        errors.add( new TokenError(
                possibleToken.getPossibleToken(),
                null,
                language,
                index.getRow(),
                index.getColumn()
        ));
        getPossibleToken().reStart();
    }
}
