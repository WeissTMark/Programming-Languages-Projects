package weiss_mark;

import weiss_mark.visitor.visitor;

public class food extends cell{
    public food () {
        type = "food";
        chara = "✽";
    }
    @Override
    public void accept(visitor v) {
        v.visit(this);
    }
}
