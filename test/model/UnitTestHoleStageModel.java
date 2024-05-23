package model;
import boardifier.model.Model;
import boardifier.view.ConsoleColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;

import java.util.List;


public class UnitTestHoleStageModel {
    private Model model;
    private HoleStageModel holeStageModel;
    private HoleStageFactory factory;
    private HoleBoard holeBoard;

    @BeforeEach
    void init() {
        model = new Model();
        holeStageModel = new HoleStageModel("HoleConsole", model);
        model.setGameStage(holeStageModel);
        factory = new HoleStageFactory(holeStageModel);
    }

    @Test
    void testIsValidCoordinates() {
        HoleStageModel model = new HoleStageModel("HoleStageModel", new Model());
        assertTrue(model.isValidCoordinates(0, 0));
        assertTrue(model.isValidCoordinates(8, 8));
        assertFalse(model.isValidCoordinates(-1, 0));
        assertFalse(model.isValidCoordinates(0, -1));
        assertFalse(model.isValidCoordinates(9, 0));
        assertFalse(model.isValidCoordinates(0, 9));
    }

//    @Disabled
//    @Test
//    void testFindPawnFrom() {
//        HoleStageModel mockHoleStageModel = Mockito.mock(HoleStageModel.class);
//        HoleBoard mockHoleBoard = Mockito.mock(HoleBoard.class);
//        Pawn pawn = new Pawn(0, 0, 'X', mockHoleStageModel);
//        mockHoleStageModel.setBoard(mockHoleBoard);
//        when(spyHoleStageModel.getLockedColor()).thenReturn(ConsoleColor.RED);
//        when(spyHoleStageModel.isFirstPlayer()).thenReturn(true);
//    }

    @Test
    void testGoodFromEntry(){


    }
}
