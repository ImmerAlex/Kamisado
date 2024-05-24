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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTestHoleSmartDecider {
    Model model;
    private HoleSmartDecider holeSmartDecider;
    private HoleStageModel mockedStage;
    private HoleBoard board;
    private Pawn pawn;

    @BeforeEach
    public void setup() {
        model = new Model();
        View holeView = new View(model);
        HoleController holeController = new HoleController(model, holeView);
        holeSmartDecider = new HoleSmartDecider(model, holeController, holeView);
        mockedStage = Mockito.mock(HoleStageModel.class);
        board = new HoleBoard(0, 0, 8, 8, mockedStage);
        pawn = new Pawn(0, 1, 'X', mockedStage);
    }

    @Test
    void testCanPawnMove() {
        when(mockedStage.reverseComputeX(Mockito.anyInt())).thenReturn(0);
        when(mockedStage.reverseComputeY(Mockito.anyInt())).thenReturn(0);

        when(mockedStage.getLockedColor()).thenReturn(ConsoleColor.BROWN_BACKGROUND);
        assertFalse(holeSmartDecider.canPawnMove(mockedStage, board, pawn, "Player X"));

        when(mockedStage.reverseComputeX(Mockito.anyInt())).thenReturn(0);
        when(mockedStage.reverseComputeY(Mockito.anyInt())).thenReturn(4);
        board.addElement(pawn, 5, 0);

        when(mockedStage.getLockedColor()).thenReturn(ConsoleColor.BROWN_BACKGROUND);
        assertTrue(holeSmartDecider.canPawnMove(mockedStage, board, pawn, "Player X"));

        when(mockedStage.getLockedColor()).thenReturn(ConsoleColor.BLUE_BACKGROUND);
        assertFalse(holeSmartDecider.canPawnMove(mockedStage, board, pawn, "Player X"));
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

        assertTrue(holeSmartDecider.noOneCanMove(stage, board));
    }
}