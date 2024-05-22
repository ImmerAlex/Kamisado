package model;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.TextElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.mock.*;

import java.awt.*;
import java.util.List;

public class UnitTestHoleBoard {
    private HoleBoard holeBoard;
    private HoleStageModel mockGameStage;
    private GameStageModel mockGameStageModel;
    private Model model;
    private HoleStageModel stageModel;
    private HoleStageFactory factory;

    @BeforeEach
    public void setup() {
        model = new Model();
        stageModel = new HoleStageModel("HoleConsole", model);
        factory = new HoleStageFactory(stageModel);

        model.addHumanPlayer("Player X");
        model.addComputerPlayer("Player O");

        factory.setup();

        holeBoard = stageModel.getBoard();
        mockGameStage = mock(HoleStageModel.class);
        mockGameStageModel = mock(GameStageModel.class);

        when(mockGameStage.getBoard()).thenReturn(holeBoard);
    }

    @Test
    void testComputeValidCellsForPlayerX() {
        when(mockGameStage.getBoard()).thenReturn(holeBoard);
        when(mockGameStage.getCurrentPlayerName()).thenReturn("Player X");

        List<Point> result = holeBoard.computeValidCells(mockGameStage, 0, 0);
        System.out.println(result);
    }

    @Disabled
    @Test
    void testComputeValidCellsForPlayerO() {
        when(mockGameStage.getBoard()).thenReturn(holeBoard);
        when(mockGameStage.getCurrentPlayerName()).thenReturn("Player O");

        List<Point> result = holeBoard.computeValidCells(mockGameStage, 0, 7);
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
