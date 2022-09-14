package weiss_mark;

import weiss_mark.visitor.visitor;

public class habitat extends cell{
    public habitat () {
        type = "habitat";
        chara = "☁";
    }

    @Override
    public void accept(visitor v) {
        v.visit(this);
    }
}
