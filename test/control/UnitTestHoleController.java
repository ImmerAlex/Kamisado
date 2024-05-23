//package control;
//
//import boardifier.control.ActionFactory;
//import boardifier.control.ActionPlayer;
//import boardifier.control.StageFactory;
//import boardifier.model.*;
//import boardifier.model.action.ActionList;
//import boardifier.view.ConsoleColor;
//import boardifier.view.View;
//import control.HoleController;
//import model.HoleStageModel;
//import org.junit.jupiter.api.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//
//import boardifier.control.StageFactory;
//import boardifier.model.GameException;
//import boardifier.model.Model;
//import boardifier.view.View;
//import control.AISelector;
//import control.HoleController;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.BufferedReader;
//
//public class UnitTestHoleController {
//
//    private Model model;
//    private HoleController holeController;
//
//    private HoleStageModel StageModel;
//    private static AISelector aiSelector;
//    private BufferedReader consoleIn;
//
//
//    @BeforeEach
//    public void setUp() {
//        model = new Model();
//        model.addHumanPlayer("Player X");
//        model.addComputerPlayer("Computer O");
//
//        StageFactory.registerModelAndView("KamisadoTest",
//                "model.HoleStageModel",
//                "view.HoleStageView");
//        View holeView = new View(model);
//        holeController = new HoleController(model, holeView);
//        holeController.setFirstStageName("KamisadoTest");
//
//        try{
//            holeController.startGame();
//            holeController.stageLoop();
//        } catch (GameException e) {
//            System.out.println("Cannot start the game. Abort!");;
//        }
//    }
//
//
//    @Test
//    public void testPlayTurnForComputerX(){
//        StageModel = Mockito.mock(HoleStageModel.class);
//        when(StageModel.getCurrentPlayerName()).thenReturn("Computer X");
//        Assertions.assertEquals("Computer X",StageModel.getCurrentPlayerName());
//
//
//
//    }
//
//    @Test
//    public void testPlayTurnForPlayerX(){
//        StageModel = Mockito.mock(HoleStageModel.class);
//        when(StageModel.getCurrentPlayerName()).thenReturn("Player X");
//        Assertions.assertEquals("Player X",StageModel.getCurrentPlayerName());
//
//
//
//
//    }
//
//
//    @Test
//    public void testPlayTurnForComputerO(){
//        StageModel = Mockito.mock(HoleStageModel.class);
//        when(StageModel.getCurrentPlayerName()).thenReturn("Computer O");
//        Assertions.assertEquals("Computer O",StageModel.getCurrentPlayerName());
//
//
//
//
//    }
//
//    @Test
//    public void testPlayTurnForPlayerO(){
//        StageModel = Mockito.mock(HoleStageModel.class);
//        when(StageModel.getCurrentPlayerName()).thenReturn("Player O");
//        Assertions.assertEquals("Player O",StageModel.getCurrentPlayerName());
//
//
//
//
//    }
//
//}
