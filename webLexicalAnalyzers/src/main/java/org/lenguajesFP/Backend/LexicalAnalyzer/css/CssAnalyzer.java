package org.lenguajesFP.Backend.LexicalAnalyzer.css;

import org.lenguajesFP.Backend.LexicalAnalyzer.*;
import org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer.*;
import org.lenguajesFP.Backend.TokenError;
import org.lenguajesFP.Backend.exceptions.InvalidTokenException;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

public class CssAnalyzer extends LexicalAnalyzer {

    private Combinator combinator;
    private Other other;
    private Rule rule;
    private TagOrType tagOrType;
    private Universal universal;
    private OfClassOrOfId ofClass;
    private LanguageTypeAnalyzer languageTypeAnalyzer;
    private HexadecimalColor hexadecimalColor;
    private RgbColor rgbColor;
    private Identifier identifier;
    private IntegerAnalyzer integerAnalyzer;

    public void readCss(LanguageTypeAnalyzer languageTypeAnalyzer) throws LexicalAnalyzerException {
        this.languageTypeAnalyzer = languageTypeAnalyzer;
        initVars(this.languageTypeAnalyzer);
        combinator = new Combinator(this.languageTypeAnalyzer);
        other = new Other(this.languageTypeAnalyzer);
        rule = new Rule(this.languageTypeAnalyzer);
        tagOrType = new TagOrType(this.languageTypeAnalyzer);
        universal = new Universal(this.languageTypeAnalyzer);
        ofClass = new OfClassOrOfId(this.languageTypeAnalyzer);
        hexadecimalColor = new HexadecimalColor(this.languageTypeAnalyzer);
        rgbColor = new RgbColor(this.languageTypeAnalyzer);
        identifier = new Identifier(this.languageTypeAnalyzer);
        integerAnalyzer = new IntegerAnalyzer(this.languageTypeAnalyzer);
        initState();
    }

    private void initState() throws LexicalAnalyzerException {

         if (current() == '>' && input[index.get() + 1] == '>'){//cambio de estado
            languageTypeAnalyzer.read(tokens,errors,outputCode,input,index, htmlTokens, cssTokens, jsTokens);

         } else if (current() == '/'){//cometario
            CommentAnalyzer commentAnalyzer = new CommentAnalyzer(languageTypeAnalyzer);
            commentAnalyzer.readComment("CSS");
            initState();

         } else if (isSpace(input[index.get()]) && current() != ' '){//tabulacion o salto de linea
             outputCode.add(String.valueOf(current()));
             next();
             initState();
         } else if (current() == '`') {//cadena
             StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer,tokens,"CSS",'`','`');
             System.out.println("el valor del indice es en css analyzer "+index);
             try {
                 if (stringAnalyzer.readString()){
                     outputCode.add(possibleToken.getPossibleToken());
                     possibleToken.reStart();
                     next();
                     initState();
                 }
             }catch (InvalidTokenException e){
                 errors.add(e.getError());
                 next();
                 initState();
             }
         } else if (current() == '\'') {//cadena
             StringAnalyzer stringAnalyzer = new StringAnalyzer(languageTypeAnalyzer,tokens,"CSS",'\'','\'');
             try {
                 if (stringAnalyzer.readString()){
                     outputCode.add(possibleToken.getPossibleToken());
                     possibleToken.reStart();
                     next();
                     initState();
                 }
             }catch (InvalidTokenException e){
                 errors.add(e.getError());
                 next();
                 initState();
             }
         } else if (ofClass.isToken('.')){//identificador
             ofClass.saveToken();
             initState();
         } else if (current() == '#'){
              if (hexadecimalColor.isToken()) {//color hexadecimal
                 hexadecimalColor.saveToken();
                 initState();
             } else if (ofClass.isToken('#')) {
                 ofClass.saveToken();
                 initState();
             } else {

              }
         }  else {//es un caracter
            characterState();
        }
    }

    private void characterState() throws LexicalAnalyzerException {

        if (combinator.isCharacterToken()){ //el caracter puede ser un token del tipo combinador
            //isPossibleToken();
            combinator.saveToken();
            next();
            initState();
        } else if (other.isCharacterToken()){// el caraceter puede ser un token del tipo otros
            other.saveToken(String.valueOf(current()));
            outputCode.add(String.valueOf(current()));
            next();
            initState();
        } else if (universal.isCharacterToken()){// el caracter puede ser un token del tipo universal
            universal.saveToken();
            next();
            initState();
        } else if (isSpace(current()) && possibleToken.getPossibleToken() != null){//termina la palabra y esta puede ser un token
            next();
            initState();
        } else if (isSpace(current())){// si el espacio no es token
            outputCode.add(String.valueOf(current()));
            next();
            initState();
        } else {
            concat();
            isException();
        }
    }

    private void isException() throws LexicalAnalyzerException {
        if (other.isExceptionWord()){
            other.saveToken(possibleToken.getPossibleToken());
            outputCode.add(possibleToken.getPossibleToken());
            possibleToken.reStart();
            initState();
        } else if (rgbColor.isToken()){
            rgbColor.saveToken();
            initState();
        } else {
            System.out.println("no es una exepcion "+possibleToken.getPossibleToken());
            isPossibleToken();
            next();
            initState();
        }
    }

    private void isPossibleToken() throws LexicalAnalyzerException {
        System.out.println("actual "+current());
        if (possibleToken.getPossibleToken() != null){
            if (tagOrType.isToken()){
                tagOrType.saveToken();
            } else if (other.isToken()){
                other.saveToken(possibleToken.getPossibleToken());
                outputCode.add(possibleToken.getPossibleToken());
                possibleToken.reStart();
            }  else if (rule.isKey()) {
                next();
                initState();
            }else if (rule.isToken()) {
                rule.saveToken();
            }else if (identifier.isToken()) {//
                identifier.saveToken();
                initState();
            } else if (integerAnalyzer.isToken()) {
                integerAnalyzer.saveToken("CSS");
                next();
                initState();
            } else if (isSpace(input[index.get()+1])) {
                saveError();
            }
        }
    }

    private void saveError(){
        errors.add(new TokenError(
                possibleToken.getPossibleToken(),
                null,
                "CSS",
                index.getRow(),
                index.getColumn()
        ));
        possibleToken.reStart();
    }
}