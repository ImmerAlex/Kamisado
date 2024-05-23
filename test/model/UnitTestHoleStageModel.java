package model;

import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.HoleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;


public class UnitTestHoleStageModel {
    Model model;
    HoleController holeController;
    Thread gameThread;

    @BeforeEach
    public void setUp() {
        model = new Model();

        StageFactory.registerModelAndView("KamisadoTest",
                "model.HoleStageModel",
                "view.HoleStageView");
        View holeView = new View(model);
        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");

    }

    @Test
    void testIsWinPlayerX(){
        model.addHumanPlayer("Player X");
        model.addHumanPlayer("Player O");
        String simulatedInput =
                "D8\n" +
                "D3\n" +
                "E2\n" +
                "G7\n" +
                "A7\n" +
                "F1";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            gameThread = new Thread(() -> {
                try {
                    holeController.startGame();
                    holeController.stageLoop();

                } catch (GameException e) {
                    System.out.println("Cannot start the game. Abort!");
                }
            });
            gameThread.start();

            gameThread.join();

            int winnerId = model.getIdWinner();

            String winnerName = model.getPlayers().get(winnerId).getName();

            assertEquals("Player X", winnerName);

            Thread.sleep(100);
        } catch (Exception e) {
            model.stopStage();
            gameThread.interrupt();
            System.out.println("fin");
            }
        }

    @Test
    void testIsWinPlayerO(){
        model.addHumanPlayer("Player X");
        model.addHumanPlayer("Player O");
        String simulatedInput =
                "G8\n" +
                        "D5\n" +
                        "H7\n" +
                        "C5\n" +
                        "G8\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            gameThread = new Thread(() -> {
                try {
                    holeController.startGame();
                    holeController.stageLoop();

                } catch (GameException e) {
                    System.out.println("Cannot start the game. Abort!");
                }
            });
            gameThread.start();

            gameThread.join();

            int winnerId = model.getIdWinner();

            String winnerName = model.getPlayers().get(winnerId).getName();

            assertEquals("Player O", winnerName);

            Thread.sleep(100);
        } catch (Exception e) {
            model.stopStage();
            gameThread.interrupt();
            System.out.println("fin");
        }
    }

    @Test
    void testIsWinComputerX(){
        model.addHumanPlayer("Computer X");
        model.addHumanPlayer("Computer O");
        String simulatedInput =
                "D8\n" +
                        "D3\n" +
                        "E2\n" +
                        "G7\n" +
                        "A7\n" +
                        "F1";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            gameThread = new Thread(() -> {
                try {
                    holeController.startGame();
                    holeController.stageLoop();

                } catch (GameException e) {
                    System.out.println("Cannot start the game. Abort!");
                }
            });
            gameThread.start();

            gameThread.join();

            int winnerId = model.getIdWinner();

            String winnerName = model.getPlayers().get(winnerId).getName();

            assertEquals("Computer X", winnerName);

            Thread.sleep(100);
        } catch (Exception e) {
            model.stopStage();
            gameThread.interrupt();
            System.out.println("fin");
        }
    }

    @Test
    void testIsWinComputerO(){
        model.addHumanPlayer("Computer X");
        model.addHumanPlayer("Computer O");
        String simulatedInput =
                "G8\n" +
                        "D5\n" +
                        "H7\n" +
                        "C5\n" +
                        "G8\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        try {
            gameThread = new Thread(() -> {
                try {
                    holeController.startGame();
                    holeController.stageLoop();

                } catch (GameException e) {
                    System.out.println("Cannot start the game. Abort!");
                }
            });
            gameThread.start();

            gameThread.join();

            int winnerId = model.getIdWinner();

            String winnerName = model.getPlayers().get(winnerId).getName();

            assertEquals("Computer O", winnerName);

            Thread.sleep(100);
        } catch (Exception e) {
            model.stopStage();
            gameThread.interrupt();
            System.out.println("fin");
        }
    }
}
