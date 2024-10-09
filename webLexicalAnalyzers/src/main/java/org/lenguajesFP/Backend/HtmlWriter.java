package org.lenguajesFP.Backend;

public class HtmlWriter{

    public static final String FIRST_PART =
            """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <style>""";

    public static final String SECOND_PART =
            """
             </style>
            
            <script>""";
    public static final String THIRD_PART =
                    """
                    </script>
                    </head>
                    <body>""";

    public static final String FOURTH_PARTH =
                    """
                    </body>
                    </html>""";

    public String getHtmlResult(String html, String css, String js){
        return FIRST_PART +"\n"+
                css +"\n"+
                SECOND_PART +"\n"+
                js +"\n"+
                THIRD_PART +"\n"+
                html +"\n"+
                FOURTH_PARTH;
    }
}
