package weiss_mark;

import weiss_mark.visitor.visitor;

public class fish extends cell{
    public fish () {
        type = "fish";
        chara = "∝";
    }

    @Override
    public void accept(visitor v) {
        v.visit(this);
    }
}
