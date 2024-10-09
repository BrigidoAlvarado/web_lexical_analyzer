package org.lenguajesFP.Backend;

import org.lenguajesFP.Backend.LexicalAnalyzer.Optimizer;
import org.lenguajesFP.Backend.exceptions.LexicalAnalyzerException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class Writer {
    private Reader reader;
    private String html;
    private String css;
    private String js;

    private String htmlOptimized;
    private String cssOptimized;
    private String jsOptimized;

    public String generateAndTranslate(String input)throws LexicalAnalyzerException {

        reader = new Reader();
        reader.readCode(input);
         html = String.join("", reader.getHtmlTokens());
         css = String.join("", reader.getCssTokens());
         js = String.join("", reader.getJsTokens());

        return ("<<HTML>>\n"+html+"\n<<CSS>>\n"+css+"\n<<JAVASCRIPT>>"+js);
    }

    public String optimize(){
        Optimizer optimizer = new Optimizer();
        htmlOptimized = optimizer.optimizeCode(html);
        cssOptimized = optimizer.optimizeCode(css);
        jsOptimized = optimizer.optimizeCode(js);
        return (  "                                       <<<<<<<<<<<HTML-optimizado>>>>>>>>>>\n"+htmlOptimized+
                "\n                                       <<<<<<<<<<<<CSS-optimizado>>>>>>>>>>\n"+cssOptimized+
                "\n                                       <<<<<<<<JAVASCRIPT-optimizado>>>>>>>\n"+jsOptimized);
    }

    public void export(String path) throws LexicalAnalyzerException {
        String title = "Html-Generado";
        HtmlWriter htmlWriter = new HtmlWriter();
        File fileHtml = new File(path + File.separatorChar + title + LocalDateTime.now() + ".html");
        try (FileWriter fileWriter = new FileWriter(fileHtml)){
            fileWriter.write(htmlWriter.getHtmlResult(htmlOptimized,cssOptimized,jsOptimized));
        } catch (IOException e) {
            throw new LexicalAnalyzerException("Error al escribir el archivo html generado");
        }
    }

    public  Reader getReader(){
        return reader;
    }
}
