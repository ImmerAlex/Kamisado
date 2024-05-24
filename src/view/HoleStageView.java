package view;

import boardifier.control.Logger;
import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import model.HoleStageModel;

/**
 * HoleStageView has to create all the looks for all game elements created by the HoleStageFactory.
 * <p>
 * The UI constraints are :
 * - the main board has double-segments border, coordinates, and cells of size 2x4
 * - the black pot has double-segments border, will cells that resize to match what is within (or not)
 * - the red pot has simple-segment border, and cells have a fixed size of 2x4
 * <p>
 * main board can be instanciated directly as a ClassicBoardLook.
 * black pot could be instanciated directly as a TableLook, but in this demo a BlackPotLook subclass is created (in case of we want to modifiy the look in some way)
 * for red pot, a subclass RedPotLook of GridLook is used, in order to override the method that render the borders.
 */

public class HoleStageView extends GameStageView {
    public HoleStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        HoleStageModel model = (HoleStageModel) gameStageModel;

        addLook(new TextLook(model.getPlayerName()));

        addLook(new ColoredBoardLook(2, 8, model.getBoard(), 1, 1, true));

        model.getXPawns().forEach(pawn -> addLook(new PawnLook(pawn)));

        model.getOPawns().forEach(pawn -> addLook(new PawnLook(pawn)));

        Logger.debug("Finished creating looks for HoleStageView", this);
    }
}
