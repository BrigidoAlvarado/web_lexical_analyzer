package org.lenguajesFP.Backend.LexicalAnalyzer;

public class PossibleToken {
    private String possibleToken;

    public void concat(char c){
        if (possibleToken == null){
            possibleToken = String.valueOf(c);
        } else{
            possibleToken += c;
        }
    }

    public void reStart(){
        possibleToken = null;
    }

    public String getPossibleToken() {
        return possibleToken;
    }
}
