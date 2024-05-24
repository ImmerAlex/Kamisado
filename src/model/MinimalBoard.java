package model;

public class MinimalBoard {
    public char symbol;
    public int idColor;


    public MinimalBoard(char symbol, int idColor) {
        this.symbol = symbol;
        this.idColor = idColor;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getIdColor() {
        return idColor;
    }
}
