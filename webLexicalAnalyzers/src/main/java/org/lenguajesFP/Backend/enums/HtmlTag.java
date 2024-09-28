package org.lenguajesFP.Backend.enums;

public enum HtmlTag {

    principal(false,"main"),
    encabezado(false, "header"),
    navegacion(false, "nav"),
    apartado(false, "aside"),
    listaordenada(false, "ul"),
    listadesordenada(false, "ol"),
    itemlista(false, "li"),
    anclaje(false, "a"),
    contenedor(false, "div"),
    seccion(false, "section"),
    articulo(false, "article"),
    titulo1(false, "h1"),
    titulo2(false, "h2"),
    titulo3(false, "h3"),
    titulo4(false, "h4"),
    titulo5(false, "h5"),
    titulo6(false, "h6"),
    parrafo(false, "p"),
    span(false, "span"),
    formulario(false, "form"),
    label(false, "label"),
    boton(false, "button"),
    piepagina(false, "footer"),
    entrada(true, "input"),
    area(true, "textarea");

    private final boolean isLine;
    private final String translate;

    HtmlTag(boolean isLine, String translate){
        this.isLine = isLine;
        this.translate = translate;
    }

    public boolean isLine(){
        return isLine;
    }

    public String getTranslate(){
        return translate;
    }


}
