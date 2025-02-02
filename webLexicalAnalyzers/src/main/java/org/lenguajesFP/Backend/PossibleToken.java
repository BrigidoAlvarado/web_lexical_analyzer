package org.lenguajesFP.Backend;

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

    public void setPossibleToken(String possibleToken) {
        this.possibleToken = possibleToken;
    }
    public void removeLastChar(){
        possibleToken = possibleToken.substring(0, possibleToken.length() - 1);
    }

}
