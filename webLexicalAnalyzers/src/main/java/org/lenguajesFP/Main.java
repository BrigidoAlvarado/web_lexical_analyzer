package org.lenguajesFP;

import org.lenguajesFP.Backend.Reader;

public class Main {
    public static void main(String[] args) {
        String text = "             >>[html] ";
        Reader reader = new Reader();
        reader.readCode(text);
    }
}