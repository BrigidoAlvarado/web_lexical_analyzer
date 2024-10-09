package org.lenguajesFP.Backend.reports;

import org.lenguajesFP.Backend.*;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.util.ArrayList;
import java.util.List;

public class ReportsGenerator {

    private List<Token> tokens;
    private List<TokenError> errors;
    private List<String> tittles;
    private List<List<String>> rows  = new ArrayList<List<String>>();
    private HTMLTableGenerator tableGenerator = new HTMLTableGenerator();;
    private Writer htmlWriter = new Writer();
    private String htmlContent;
    private String tittlePage;

    public String TokensReport(List<Token> tokens,String path)throws LexicalAnalyzerException {
        this.tokens = tokens;
        tittlePage = "Reporte de Tokens";
        tittles = List.of("Token","Expresion Regular","Lenguaje","Tipo","Fila","Columna");
        for(Token token : tokens){
            System.out.println("ingresando "+token);
            List<String> row = new ArrayList<>();
            row.add(token.getLexeme());
            row.add(token.getRegularExpression());
            row.add(token.getLenguage());
            row.add(token.getType());
            row.add(String.valueOf(token.getRow()));
            row.add(String.valueOf(token.getCol()));
            System.out.println("lista guardada "+row);
            rows.add(row);
        }
        htmlContent = tableGenerator.generateHTMLTable(tittlePage, tittles, rows);
        return htmlWriter.exportReports(path,"Reporte de Tokens",htmlContent);
    }

    public String ErrorsReport(List<TokenError> errors,String path)throws LexicalAnalyzerException {
        this.errors = errors;
        tittlePage = "Reporte de Errores";
        tittles = List.of("Token","Lenguaje donde se Encontro","Lenguaje sugerido","Fila","Columna");
        for(TokenError token : errors){
            List<String> row = new ArrayList<>();
            row.add(token.getLexeme());
            row.add(token.getLenguage());
            row.add(" ");
            row.add(String.valueOf(token.getRow()));
            row.add(String.valueOf(token.getCol()));
            rows.add(row);
        }
        htmlContent = tableGenerator.generateHTMLTable(tittlePage, tittles, rows);
        return htmlWriter.exportReports(path,"Reporte de Errores",htmlContent);
    }

    public String optimizeReports(List<Token> tokens,String path)throws LexicalAnalyzerException {
        this.tokens = tokens;
        tittlePage = "Reporte de Optimizacion";
        tittles = List.of("Token","Expresion Regular","Lenguaje","Tipo","Fila","Columna");

        List<Integer> deletedRows = new ArrayList<>();
        for(Token token : tokens){
            if(token.getType().equalsIgnoreCase("Comentario")){
                deletedRows.add(token.getRow());
            }
        }
        List<Token> deletedTokens = new ArrayList<>();
        for(Integer row : deletedRows){
            for (Token token : tokens){
                if(token.getRow() == row){
                    deletedTokens.add(token);
                }
            }
        }
        System.out.println(deletedTokens);
        for(Token token : deletedTokens){
            List<String> row = new ArrayList<>();
            row.add(token.getLexeme());
            row.add(token.getRegularExpression());
            row.add(token.getLenguage());
            row.add(token.getType());
            row.add(String.valueOf(token.getRow()));
            row.add(String.valueOf(token.getCol()));
            rows.add(row);
        }
        htmlContent = tableGenerator.generateHTMLTable(tittlePage, tittles, rows);
        return htmlWriter.exportReports(path,tittlePage,htmlContent);
    }
}
