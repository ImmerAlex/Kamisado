package model;

import boardifier.model.Model;
import boardifier.model.TextElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class UnitTestHoleStageFactory {
    private Model model;
    private HoleStageModel stageModel;
    private HoleStageFactory factory;
    private TextElement text;

    @BeforeEach
    void setUp() {
        model = new Model();
        stageModel = new HoleStageModel("HoleConsole", model);
        factory = new HoleStageFactory(stageModel);

        model.addHumanPlayer("Player X");
        model.addComputerPlayer("Player O");

        factory.setup();
    }

    @Test
    void testSetup() {
        text = stageModel.getPlayerName();
        assertNotNull(text);
        assertEquals(0, text.getX());
        assertEquals(0, text.getY());


        HoleBoard board = stageModel.getBoard();
        assertNotNull(board);
        assertEquals(0, board.getX());
        assertEquals(1, board.getY());
        assertEquals(8, board.getNbRows());
        assertEquals(8, board.getNbCols());

        List<Pawn> OPawns = stageModel.getOPawns();
        assertNotNull(OPawns);
        assertEquals(8, OPawns.size());
        for (int i = 0; i < 8; i++) {
            Pawn pawn = OPawns.get(i);
            assertEquals(0, pawn.getNumber());
            assertEquals(8 - i, pawn.getColor());
            assertEquals('O', pawn.getSymbol());
        }

        List<Pawn> XPawns = stageModel.getXPawns();
        assertNotNull(XPawns);
        assertEquals(8, XPawns.size());
        for (int i = 0; i < 8; i++) {
            Pawn pawn = XPawns.get(i);
            assertEquals(0, pawn.getNumber());
            assertEquals(i + 1, pawn.getColor());
        }
    }
}