import boardifier.model.GameException;
import boardifier.view.View;
import control.HoleController;

import boardifier.control.StageFactory;
import boardifier.model.Model;

public class HoleConsole {

    public static void main(String[] args) {
//        Logger.setLevel(Logger.LOGGER_TRACE);
//        Logger.setVerbosity(Logger.VERBOSE_HIGH);

        int mode = 0;
        if (args.length == 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode <0) || (mode>2)) mode = 0;
            }
            catch(NumberFormatException ignored) {}
        }

        Model model = new Model();

        if (mode == 0) {
            model.addHumanPlayer("Player X");
            model.addHumanPlayer("Player O");
        } else if (mode == 1) {
            model.addHumanPlayer("Player X");
            model.addComputerPlayer("Computer O");
        } else {
            model.addComputerPlayer("Computer X");
            model.addComputerPlayer("Computer O");
        }

        StageFactory.registerModelAndView("Kamisado", "model.HoleStageModel", "view.HoleStageView");
        View holeView = new View(model);
        HoleController control = new HoleController(model,holeView);
        control.setFirstStageName("Kamisado");

        try {
            control.startGame();
            control.stageLoop();
        }
        catch(GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
    }
}
