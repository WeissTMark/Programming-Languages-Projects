package weiss_mark.visitor;

import weiss_mark.*;

public class color implements visitor {
    int fishCount;
    int foodCount;
    int emptyCount;
    int habitatCount;

    public void setCounts(int[] cnts) {
        //Assuming correct input from count list
        emptyCount = cnts[0];
        habitatCount = cnts[1];
        foodCount = cnts[2];
        fishCount = cnts[3];
    }

    @Override
    public void visit(habitat h) {
    }

    @Override
    public void visit(fish f) {
        if (fishCount < foodCount && fishCount < habitatCount) {
            //Green markishotuwu
            f.setColor(ColorText.Color.GREEN);
        } else if (fishCount > foodCount && fishCount > habitatCount) {
            //Red
            f.setColor(ColorText.Color.RED);
        } else {
            //Yellow
            f.setColor(ColorText.Color.YELLOW);
        }
    }

    @Override
    public void visit(food f) {
    }

    @Override
    public void visit(cell c) {
    }
}
