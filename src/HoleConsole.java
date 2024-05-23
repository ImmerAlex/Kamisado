import java.util.Scanner;

import boardifier.model.GameException;
import boardifier.view.View;
import control.HoleController;

import boardifier.control.StageFactory;
import boardifier.model.Model;

public class HoleConsole {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
//        Logger.setLevel(Logger.LOGGER_TRACE);
//        Logger.setVerbosity(Logger.VERBOSE_HIGH);

        System.out.println("Choose the game mode:");
        System.out.println("\t0: Human vs Human");
        System.out.println("\t1: Human vs Computer");
        System.out.println("\t2: Computer vs Computer");

        int mode = -1;
        do {
            System.out.print("Your choice: ");
            String choice = input.nextLine();
            if (choice.equals("0")) {
                mode = 0;
                break;
            } else if (choice.equals("1")) {
                mode = 1;
                break;
            } else if (choice.equals("2")) {
                mode = 2;
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (true);

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
