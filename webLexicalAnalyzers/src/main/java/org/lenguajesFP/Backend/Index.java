package org.lenguajesFP.Backend;

public class Index {
    private int index = 0;
    private int row = 1;
    private int column = 1;

    @Override
    public String toString() {
        return String.valueOf(index);
    }

    public int get() {
        return index;
    }

    public void increaseRow(){
        row++;
        column = 1;
    }

    public void increaseColumn(){
        column++;
    }

    public void increase(){
        index++;
    }

    public void decrease(){
        index--;
    }

    public int getRow() {
        return row;
    }

    public int getColumn( ) {
        return column;
    }

    public void reStart(int index, int row, int column){
        this.index = index;
        this.row = row;
        this.column = column;
    }
}
