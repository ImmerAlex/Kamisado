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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTestHoleSmartDecider {
    Model model;
    View holeView;
    HoleController holeController;
    AISelector aiSelector;

    @BeforeEach
    public void setup() {
        model = new Model();
        model.addComputerPlayer("Computer X");
        model.addComputerPlayer("Computer O");
        EntryFileContainer.addEntry("2");
        EntryFileContainer.addEntry("2");

        StageFactory.registerModelAndView("KamisadoTest", "model.HoleStageModel","view.HoleStageView");

        holeView = new View(model);


        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");
    }

    @Test
    void testNoOneCanMoveTrue() {
        HoleSmartDecider holeSmartDecider = new HoleSmartDecider(model, holeController, holeView);
        HoleStageModel mockedStage = mock(HoleStageModel.class);

        HoleBoard board =  new HoleBoard(0, 0, 8, 8, mockedStage);

        for (int i = 0; i < 8; i++) {
            board.addElement(new Pawn(0, 1, 'X', mockedStage), 5, i);
        }

        for (int i = 0; i < 8; i++) {
            board.addElement(new Pawn(0, 1, 'O', mockedStage), 4, i);
        }

        assertTrue(holeSmartDecider.noOneCanMove(mockedStage, board));
    }

    @Test
    void testNoOneCanMoveFalse() {
        HoleSmartDecider holeSmartDecider = new HoleSmartDecider(model, holeController, holeView);
        HoleStageModel mockedStage = mock(HoleStageModel.class);

        HoleBoard board =  new HoleBoard(0, 0, 8, 8, mockedStage);

        List<Pawn> Xpawns = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(0, 1, 'X', mockedStage);
            board.addElement(pawn, 7, i);
            Xpawns.add(pawn);
        }

        List<Pawn> Opawns = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(0, 1, 'O', mockedStage);
            board.addElement(pawn, 0, i);
            Opawns.add(pawn);
        }

        when(mockedStage.getXPawns()).thenReturn(Xpawns);
        when(mockedStage.getOPawns()).thenReturn(Opawns);

        assertFalse(holeSmartDecider.noOneCanMove(mockedStage, board));
    }

    @Test
    void testCanPawnMoveTrue() {
        HoleSmartDecider holeSmartDecider = new HoleSmartDecider(model, holeController, holeView);
        HoleStageModel mockedStage = mock(HoleStageModel.class);
        HoleBoard board =  new HoleBoard(0, 0, 8, 8, mockedStage);
        Pawn pawn = new Pawn(0, 1, 'X', mockedStage);
        board.addElement(pawn, 7, 0);

        when(mockedStage.getLockedColor()).thenReturn(ConsoleColor.BROWN_BACKGROUND); // Replace "ColorString" with the actual color string
        assertTrue(holeSmartDecider.canPawnMove(mockedStage, board, pawn, "Player X"));
    }
}
