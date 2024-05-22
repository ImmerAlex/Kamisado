package model;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.TextElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.mock.*;

import java.awt.*;
import java.util.ArrayList;

public class UnitTestHoleBoard {
    private HoleBoard holeBoard;
    private HoleStageModel mockGameStage;
    private GameStageModel mockGameStageModel;

    @BeforeEach
    public void setup() {
        mockGameStage = mock(HoleStageModel.class);
        mockGameStageModel = mock(GameStageModel.class);
        holeBoard = new HoleBoard(0, 0, 3, 3, mockGameStageModel);
    }

    @Test
    void testComputeValidCells() {
        when(mockGameStage.getBoard()).thenReturn(holeBoard);
        when(mockGameStage.getCurrentPlayerName()).thenReturn("Player X");
    }

    @Test
    void testCopy() {
        HoleBoard copy = holeBoard.copy();
        assertEquals(holeBoard.getNbCols(), copy.getNbCols());
        assertEquals(holeBoard.getNbRows(), copy.getNbRows());
        assertEquals(holeBoard.getX(), copy.getX());
        assertEquals(holeBoard.getY(), copy.getY());
        assertEquals(holeBoard.getName(), copy.getName());
    }
}
