package control;

import boardifier.model.Model;
import boardifier.view.ConsoleColor;
import boardifier.view.View;
import model.HoleBoard;
import model.HoleStageModel;
import model.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        HoleStageModel stage = new HoleStageModel("test", model) {
            @Override
            public int reverseComputeX(int x) {
                return 0;
            }

            @Override
            public int reverseComputeY(int x) {
                return 0;
            }

            @Override
            public String getLockedColor() {
                return ConsoleColor.BROWN_BACKGROUND;
            }
        };

        assertFalse(stage.canPawnMove(stage, board, pawn, "Player X"));

        stage = new HoleStageModel("test", model) {
            @Override
            public int reverseComputeX(int x) {
                return 0;
            }

            @Override
            public int reverseComputeY(int x) {
                return 4;
            }

            @Override
            public String getLockedColor() {
                return ConsoleColor.BROWN_BACKGROUND;
            }
        };

        board.addElement(pawn, 5, 0);
        assertTrue(stage.canPawnMove(stage, board, pawn, "Player X"));

        stage = new HoleStageModel("test", model) {
            @Override
            public int reverseComputeX(int x) {
                return 0;
            }

            @Override
            public int reverseComputeY(int x) {
                return 4;
            }

            @Override
            public String getLockedColor() {
                return ConsoleColor.BLUE_BACKGROUND;
            }
        };

        assertFalse(stage.canPawnMove(stage, board, pawn, "Player X"));
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

        assertTrue(stage.noOneCanMove(stage, board));
    }
}