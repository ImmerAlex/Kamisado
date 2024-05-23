package control;

import boardifier.control.Decider;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import boardifier.view.View;
import model.EntryFileContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitTestAISelector {

    @Test
    void testGetDecider() {
        Model model = new Model();
        model.addComputerPlayer("Computer X");
        model.addComputerPlayer("Computer O");
        EntryFileContainer.addEntry("2");
        EntryFileContainer.addEntry("2");

        StageFactory.registerModelAndView("KamisadoTest", "model.HoleStageModel","view.HoleStageView");

        View holeView = new View(model);

        HoleController holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");

        AISelector aiSelector = holeController.getAiSelector();

        try {
            holeController.startGame();
            holeController.stageLoop();
        } catch (Exception e) {
            System.out.println("Cannot start the game. Abort!");
        }

        Decider decider = aiSelector.getDecider(0, holeController);
        assertNotNull(decider);
    }
}
