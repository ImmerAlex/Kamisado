package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.view.View;
import model.EntryFileContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AISelector {
    private final Model model;
    private final View view;

    private enum AIType {
        NAIVE,
        SMART
    }
    private static final Scanner input = new Scanner(System.in);
    private static Map<Integer, AIType> aiMap;

    public AISelector(Model model, View view) {
        this.model = model;
        this.view = view;
        aiMap = new HashMap<>();
    }

    public void selectAndSetAiType(int idPlayer) {
        System.out.println("List of type of AI :");
        System.out.println("\t1. AI naive");
        System.out.println("\t2. AI smart");

        int choice = -1;
        do {
            try {
                System.out.print("Which type of AI you want : ");
                if (EntryFileContainer.getFirstEntry() != null) {
                    choice = Integer.parseInt(EntryFileContainer.getFirstEntry());
                    EntryFileContainer.removeFirstEntry();
                    System.out.println(choice);
                } else {
                    choice = input.nextInt();
                    input.nextLine();
                }
            } catch (Exception ignore){}
        } while (choice < 0 || choice > 3);


        if (choice == 1) {
            aiMap.put(idPlayer, AIType.NAIVE);
        } else {
            aiMap.put(idPlayer, AIType.SMART);
        }
    }

    public Decider getDecider(int idPlayer, Controller control) {
        AIType type = aiMap.get(idPlayer);

        if (type == AIType.NAIVE) {
            return new HoleNaiveDecider(model, control, view);
        } else if (type == AIType.SMART) {
            return new HoleSmartDecider(model, control, view);
        }

        return null;
    }
}
