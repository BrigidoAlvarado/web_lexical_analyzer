package org.lenguajesFP.Backend;
import java.util.List;

public class HTMLTableGenerator {

    public String generateHTMLTable(String pageTitle, List<String> columnTitles, List<List<String>> rows) {
        StringBuilder html = new StringBuilder();

        // Inicia el documento HTML
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("  <meta charset='UTF-8'>\n");
        html.append("  <title>").append(pageTitle).append("</title>\n");
        html.append("  <style>\n");
        html.append("    table { width: 100%; border-collapse: collapse; }\n");
        html.append("    th, td { padding: 8px; text-align: left; border: 1px solid black; }\n");
        html.append("    th { background-color: #f2f2f2; }\n");
        html.append("  </style>\n");
        html.append("</head>\n");

        // Inicia el cuerpo del HTML
        html.append("<body>\n");
        html.append("  <h1>").append(pageTitle).append("</h1>\n");

        // Inicia la tabla
        html.append("  <table>\n");

        // Genera la fila de los títulos de las columnas
        html.append("    <tr>\n");
        for (String title : columnTitles) {
            html.append("      <th>").append(title).append("</th>\n");
        }
        html.append("    </tr>\n");

        // Genera las filas con los datos
        for (List<String> row : rows) {
            html.append("    <tr>\n");
            for (String cell : row) {
                html.append("      <td>").append(cell).append("</td>\n");
            }
            html.append("    </tr>\n");
        }

        // Finaliza la tabla
        html.append("  </table>\n");

        // Finaliza el cuerpo y el HTML
        html.append("</body>\n");
        html.append("</html>\n");

        // Retorna el HTML completo como String
        return html.toString();
    }
}

