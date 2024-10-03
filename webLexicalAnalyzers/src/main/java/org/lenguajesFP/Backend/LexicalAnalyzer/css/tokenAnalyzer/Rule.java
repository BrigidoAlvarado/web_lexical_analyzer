package org.lenguajesFP.Backend.LexicalAnalyzer.css.tokenAnalyzer;

import java.util.Arrays;
import java.util.List;

public class Rule {

    public static final List<String> RULES = Arrays.asList(
            "color",
            "background",
            "width",
            "height",
            "display",
            "inline",
            "block",
            "flex",
            "grid",
            "none",
            "margin",
            "border",
            "padding",
            "content",
            "top",
            "bottom",
            "left",
            "right",
            "auto",
            "float",
            "position");

    public static final List<String> KEYS = Arrays.asList(
            "background",
            "font",
            "min",
            "max",
            "inline",
            "border",
            "box",
            "static",
            "z",
            "justify",
            "align",
            "ist",
            "text");

    public static final List<String> COMBINED_RULES = Arrays.asList(
            "border-top, border-bottom, border-left, border-right",
            "static, relative, absolute, sticky, fixed",
            "background-color",
            "font-size",
            "font-weight",
            "font-family",
            "font-align",
            "min-width",
            "min-height",
            "max-width",
            "max-height",
            "inline-block",
            "border-color",
            "border-style",
            "border-width",
            "box-sizing",
            "border-box",
            "z-index",
            "justify-content",
            "align-items",
            "border-radius",
            "ist-style",
            "text-align",
            "box-shadow");
}
