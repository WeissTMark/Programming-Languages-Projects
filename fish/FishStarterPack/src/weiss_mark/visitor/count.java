package weiss_mark.visitor;

import weiss_mark.*;

public class count implements visitor{
    int fishCount;
    int foodCount;
    int emptyCount;
    int habitatCount;

    public int[] getCount() {
        return new int[]{emptyCount, habitatCount, foodCount, fishCount};
    }

    @Override
    public void visit(habitat h) {
        habitatCount++;
    }

    @Override
    public void visit(fish f) {
        fishCount++;
    }

    @Override
    public void visit(food f) {
        foodCount++;
    }

    @Override
    public void visit(cell c) {
        emptyCount++;
    }
}
