package weiss_mark.visitor;


import weiss_mark.*;

public interface visitor {
    public void visit(habitat h);
    public void visit(fish f);
    public void visit(food f);
    public void visit(cell c);
}
