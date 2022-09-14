package weiss_mark.visitor;

import weiss_mark.cell;
import weiss_mark.fish;
import weiss_mark.food;
import weiss_mark.habitat;

import java.util.Objects;

public class isFood implements visitor {
    private boolean isfood = false;

    public boolean isIsfood() {
        return isfood;
    }

    @Override
    public void visit(habitat h) {
        isfood = false;
    }

    @Override
    public void visit(fish f) {
        isfood = false;
    }

    @Override
    public void visit(food f) {
        isfood = true;
    }

    @Override
    public void visit(cell c) {
        isfood = false;
    }
}
