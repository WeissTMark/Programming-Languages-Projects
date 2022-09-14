package weiss_mark;
import weiss_mark.visitor.*;

public class cell {
    protected ColorText.Color color;
    protected String type = "";
    protected String chara = "⛶";

    public void accept(visitor v) {
        v.visit(this);
    }
    public ColorText.Color getColor() {return color;}
    public String getType() {
        return type;
    }
    public String getChara() {return chara;}

    public void setColor(ColorText.Color color) {
        this.color = color;
    }
}
