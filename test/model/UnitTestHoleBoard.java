package model;
import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.awt.*;
import java.util.List;

public class UnitTestHoleBoard {
    private Model model;
    private HoleStageModel stageModel;
    private HoleStageFactory factory;
    private HoleBoard holeBoard;
    private HoleStageModel mockGameStage;

    @BeforeEach
    public void setUp() {
        model = new Model();
        stageModel = new HoleStageModel("HoleConsole", model);
        factory = new HoleStageFactory(stageModel);

        model.addHumanPlayer("Player X");
        model.addHumanPlayer("Player O");

        model.addComputerPlayer("Computer X");
        model.addComputerPlayer("Computer O");

        factory.setup();

        holeBoard = stageModel.getBoard();


        mockGameStage = mock(HoleStageModel.class);

        when(mockGameStage.getBoard()).thenReturn(holeBoard);
    }

    @Test
    void testComputeValidCellsForPlayerX() {
        when(mockGameStage.getBoard()).thenReturn(holeBoard);
        when(mockGameStage.getCurrentPlayerName()).thenReturn("Player X");

        List<Point> result = holeBoard.computeValidCells(mockGameStage, 0, 7);

        assertFalse(result.isEmpty());

        for (Point point : result) {
            assertTrue(point.x >= 0 && point.x < holeBoard.getNbRows());
            assertTrue(point.y >= 0 && point.y < holeBoard.getNbCols());
        }

        List<Point> noResult = holeBoard.computeValidCells(mockGameStage, 0, 0);

        assertTrue(noResult.isEmpty());
    }

    @Test
    void testComputeValidCellsForComputerX() {
        when(mockGameStage.getBoard()).thenReturn(holeBoard);
        when(mockGameStage.getCurrentPlayerName()).thenReturn("Computer X");

        List<Point> result = holeBoard.computeValidCells(mockGameStage, 0, 7);

        assertFalse(result.isEmpty());

        for (Point point : result) {
            assertTrue(point.x >= 0 && point.x < holeBoard.getNbRows());
            assertTrue(point.y >= 0 && point.y < holeBoard.getNbCols());
        }

        List<Point> noResult = holeBoard.computeValidCells(mockGameStage, 0, 0);

        assertTrue(noResult.isEmpty());
    }

    @Test
    void testComputeValidCellsForPlayerO() {
        when(mockGameStage.getBoard()).thenReturn(holeBoard);
        when(mockGameStage.getCurrentPlayerName()).thenReturn("Player O");

        List<Point> result = holeBoard.computeValidCells(mockGameStage, 0, 0);

        assertFalse(result.isEmpty());

        for (Point point : result) {
            assertTrue(point.x >= 0 && point.x < holeBoard.getNbRows());
            assertTrue(point.y >= 0 && point.y < holeBoard.getNbCols());
        }

        List<Point> noResult = holeBoard.computeValidCells(mockGameStage, 7, 7);

        assertTrue(noResult.isEmpty());
    }

    @Test
    void testComputeValidCellsForComputerO() {
        when(mockGameStage.getBoard()).thenReturn(holeBoard);
        when(mockGameStage.getCurrentPlayerName()).thenReturn("Computer O");

        List<Point> result = holeBoard.computeValidCells(mockGameStage, 0, 0);

        assertFalse(result.isEmpty());

        for (Point point : result) {
            assertTrue(point.x >= 0 && point.x < holeBoard.getNbRows());
            assertTrue(point.y >= 0 && point.y < holeBoard.getNbCols());
        }

        List<Point> noResult = holeBoard.computeValidCells(mockGameStage, 7, 7);

        assertTrue(noResult.isEmpty());
    }
}
