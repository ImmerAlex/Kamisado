package control;

import boardifier.control.StageFactory;
import boardifier.model.Model;
import boardifier.view.ConsoleColor;
import boardifier.view.View;
import model.EntryFileContainer;
import model.HoleBoard;
import model.HoleStageModel;
import model.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class UnitTestHoleNaiveDecider {
    Model model;
    private HoleNaiveDecider holeNaiveDecider;
    private HoleStageModel mockedStage;
    HoleController holeController;
    private HoleBoard board;
    private Pawn pawn;

    @BeforeEach
    public void setup() {
        model = new Model();
        View holeView = new View(model);
        holeController = new HoleController(model, holeView);
        holeNaiveDecider = new HoleNaiveDecider(model, holeController, holeView);
        mockedStage = Mockito.mock(HoleStageModel.class);
        board = new HoleBoard(0, 0, 8, 8, mockedStage);
        pawn = new Pawn(0, 1, 'X', mockedStage);
    }

    @Test
    void testCanPawnMove() {
        when(mockedStage.reverseComputeX(anyInt())).thenReturn(0);
        when(mockedStage.reverseComputeY(anyInt())).thenReturn(0);

        when(mockedStage.getLockedColor()).thenReturn(ConsoleColor.BROWN_BACKGROUND);
        assertFalse(holeNaiveDecider.canPawnMove(mockedStage, board, pawn, "Player X"));

        when(mockedStage.reverseComputeX(anyInt())).thenReturn(0);
        when(mockedStage.reverseComputeY(anyInt())).thenReturn(4);
        board.addElement(pawn, 5, 0);

        when(mockedStage.getLockedColor()).thenReturn(ConsoleColor.BROWN_BACKGROUND);
        assertTrue(holeNaiveDecider.canPawnMove(mockedStage, board, pawn, "Player X"));

        when(mockedStage.getLockedColor()).thenReturn(ConsoleColor.BLUE_BACKGROUND);
        assertFalse(holeNaiveDecider.canPawnMove(mockedStage, board, pawn, "Player X"));
    }

    @Test
    void testNoOneCanMoveTrue() {
        HoleStageModel stage = new HoleStageModel("test", model);
        HoleBoard board = this.board;

        // Create pawns
        List<Pawn> pawnX = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(0, 1, 'X', stage);
            board.addElement(pawn, i, 6);
            pawnX.add(pawn);
        }
        stage.setXPawns(pawnX);

        List<Pawn> pawnO = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(0, 1, 'O', stage);
            board.addElement(pawn, i, 4);
            pawnO.add(pawn);
        }
        stage.setOPawns(pawnO);

        assertTrue(holeNaiveDecider.noOneCanMove(stage, board));
    }

    @Test
    void testGetRandomCoup() {
        Model model = new Model();
        model.addComputerPlayer("Computer X");
        model.addComputerPlayer("Computer O");
        EntryFileContainer.addEntry("1");
        EntryFileContainer.addEntry("1");

        StageFactory.registerModelAndView("Kamisado", "model.HoleStageModel", "view.HoleStageView");
        View holeView = new View(model);
        HoleController control = new HoleController(model, holeView);
        control.setFirstStageName("Kamisado");

        try {
            control.startGame();
            control.stageLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
