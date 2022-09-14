package weiss_mark;


import weiss_mark.visitor.feed;
import weiss_mark.visitor.isFood;
import weiss_mark.visitor.visitor;

import java.util.Objects;

public class grid {
    private final cell[][] squarea = new cell[5][5];


    public grid() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                squarea[i][j] = new cell();
            }
        }
    }

    public void accept(visitor v) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                //GRADING: NESTED
                if (v.getClass() == feed.class) {
                    if (Objects.equals(squarea[i][j].getType(), "fish")) {
                        isFood isFod = new isFood();
                        boolean fed = false;
                        // EDGE CASES YAY
                        if (i > 0) {
                            // Up
                            squarea[i - 1][j].accept(isFod);
                            if (isFod.isIsfood()) {
                                squarea[i - 1][j] = new cell();
                                fed = true;
                            }
                        }
                        if (j < 4 && !fed) {
                            //right
                            squarea[i][j + 1].accept(isFod);
                            if (isFod.isIsfood()) {
                                squarea[i][j + 1] = new cell();
                                fed = true;
                            }
                        }
                        if (i < 4 && !fed) {
                            // Down
                            squarea[i + 1][j].accept(isFod);
                            if (isFod.isIsfood()) {
                                squarea[i + 1][j] = new cell();
                                fed = true;
                            }
                        }
                        if (j > 0 && !fed) {
                            //Left
                            squarea[i][j - 1].accept(isFod);
                            if (isFod.isIsfood()) {
                                squarea[i][j - 1] = new cell();
                                fed = true;
                            }
                        }

                        if (!fed) {
                            squarea[i][j] = new cell();
                        }
                    }
                } else {
                    squarea[i][j].accept(v);
                }
            }
        }
    }

    public void setDefault() {
//      Set grid to this:
//      ✽✽✽⛶☁
//      ✽∝✽⛶⛶
//      ✽✽✽✽✽
//      ⛶⛶✽∝∝
//      ☁∝⛶⛶☁

        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                squarea[i][0] = new food();
                squarea[i][1] = new food();
                squarea[i][2] = new food();
                squarea[i][4] = new habitat();
            }
            if (i == 1) {
                squarea[i][0] = new food();
                squarea[i][1] = new fish();
                squarea[i][2] = new food();
            }
            if (i == 2) {
                for (int j = 0; j < 5; j++) {
                    squarea[i][j] = new food();
                }
            }
            if (i == 3) {
                squarea[i][2] = new food();
                squarea[i][3] = new fish();
                squarea[i][4] = new fish();
            }
            if (i == 4) {
                squarea[i][0] = new habitat();
                squarea[i][1] = new fish();
                squarea[i][2] = new cell();
                squarea[i][3] = new cell();
                squarea[i][4] = new habitat();
            }
        }
    }

    public void printGrid() {
        System.out.print("\n");
        String colored = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cell spot = squarea[i][j];
                colored = ColorText.colorString(spot.getChara(), spot.getColor());
                System.out.print(colored);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }


    public void setSquarea(int x, int y, String typ) {
        //Assuming correct input since input is validated
        if (Objects.equals(typ, "fish")) {
            squarea[x][y] = new fish();
        } else if (Objects.equals(typ, "food")) {
            squarea[x][y] = new food();
        } else if (Objects.equals(typ, "habitat")) {
            squarea[x][y] = new habitat();
        } else if (Objects.equals(typ, "")) {
            squarea[x][y] = new cell();
        }
    }
}
