package model;
import boardifier.model.Model;
import boardifier.model.TextElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.mock.*;

import java.util.ArrayList;
import java.util.List;

public class UnitTestHoleStageModel {
    @Test
    void testIsValidCoordinates() {
        HoleStageModel model = new HoleStageModel("HoleStageModel", new Model());
        assertTrue(model.isValidCoordinates(0, 0));
        assertTrue(model.isValidCoordinates(8, 8));
        assertFalse(model.isValidCoordinates(-1, 0));
        assertFalse(model.isValidCoordinates(0, -1));
        assertFalse(model.isValidCoordinates(9, 0));
        assertFalse(model.isValidCoordinates(0, 9));
    }

}
