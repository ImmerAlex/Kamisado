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

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String toString() {
        return "Symbol: " + symbol + ", idColor: " + idColor;
    }
}
