import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.HoleController;
import model.EntryFileContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HoleConsole {
    public static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        while (input.ready()) {
            String nextLine = input.readLine();
            EntryFileContainer.addEntry(nextLine);
        }

        System.out.println("Choose the game mode:");
        System.out.println("\t0: Human vs Human");
        System.out.println("\t1: Human vs Computer");
        System.out.println("\t2: Computer vs Computer");

        int mode;
        String choice;
        label:
        do {
            System.out.print("Your choice: ");
            if (EntryFileContainer.getFirstEntry() != null) {
                choice = EntryFileContainer.getFirstEntry();
                EntryFileContainer.removeFirstEntry();
                System.out.println(choice);
            } else {
                choice = input.readLine();
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
        HoleController control = new HoleController(model, holeView);
        control.setFirstStageName("Kamisado");


        try {
            control.startGame();
            control.stageLoop();
        } catch (GameException e) {
            System.out.println("Cannot start the game. Abort");
        }
    }
}
