import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import boardifier.model.GameException;
import boardifier.view.View;
import control.HoleController;

import boardifier.control.StageFactory;
import boardifier.model.Model;
import model.EntryFileContainer;

public class HoleConsole {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
//        Logger.setLevel(Logger.LOGGER_TRACE);
//        Logger.setVerbosity(Logger.VERBOSE_HIGH);
        EntryFileContainer entryFileContainer = new EntryFileContainer();

        while(input.hasNextLine()) {
            entryFileContainer.addEntry(input.nextLine());
        }

        System.out.println("Choose the game mode:");
        System.out.println("\t0: Human vs Human");
        System.out.println("\t1: Human vs Computer");
        System.out.println("\t2: Computer vs Computer");

        int mode = -1;
        String choice;
        label:
        do {
            System.out.print("Your choice: ");
            if (!entryFileContainer.getFirstEntry().isEmpty()) {
                choice = entryFileContainer.getFirstEntry();
                entryFileContainer.removeFirstEntry();
                System.out.println(choice);
            } else {
                choice = input.nextLine();
            }

            switch (choice) {
                case "0":
                    mode = 0;
                    break label;
                case "1":
                    mode = 1;
                    break label;
                case "2":
                    mode = 2;
                    break label;
                default:
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
