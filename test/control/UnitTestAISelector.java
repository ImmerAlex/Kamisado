package control;

import boardifier.control.Decider;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import boardifier.view.View;
import model.EntryFileContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitTestAISelector {
    Model model;
    HoleController holeController;
    View holeView;

    @BeforeEach
    public void setUp() {
        model = new Model();
        model.addComputerPlayer("Computer X");
        model.addComputerPlayer("Computer O");

        EntryFileContainer.addEntry("2");
        EntryFileContainer.addEntry("1");

        StageFactory.registerModelAndView("KamisadoTest", "model.HoleStageModel","view.HoleStageView");

        holeView = new View(model);

        holeController = new HoleController(model, holeView);
        holeController.setFirstStageName("KamisadoTest");
    }

    @Test
    void testGetDecider() {
        AISelector aiSelector = new AISelector(model, holeView);

        Decider decider = aiSelector.getDecider(1, holeController);
        assertEquals(new HoleNaiveDecider(model, holeController, holeView), decider);

    }
}
