package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.ConsoleColor;

/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Hole pawn is fixed.
 */
public class Pawn extends GameElement {

    private int number;
    private int color;
    private char symbol;
    public static int PAWN_X = 0;

    public Pawn(int number, int color, char symbol, GameStageModel gameStageModel) {
        super(gameStageModel);
        this.number = number;
        this.color = color;
        this.symbol = symbol;

    }

    public int getNumber() {
        return number;
    }
    public int getColor() {
        return color;
    }

    public String getStringColor() {
        return ConsoleColor.getColor(getColor());
    }

    public char getSymbol() {
        return symbol;
    }

    public String toString() {
        return "Symbol : " + symbol + ", color : " + color;
    }
}
