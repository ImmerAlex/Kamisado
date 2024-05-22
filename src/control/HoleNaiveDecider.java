package control;

import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.view.View;

import java.util.Calendar;
import java.util.Random;

public class HoleNaiveDecider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private final View view;


    public HoleNaiveDecider(Model model, Controller control, View view) {
        super(model, control);
        this.view = view;
    }

    @Override
    public ActionList decide() {
        return null;
    }
}
