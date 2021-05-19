package gcg.word.table.border;

public class Border {//FIXME
    private Type type;
    public String val = "single";
    public int sz = 4;
    public int bdrwidth = 10;
    public int space = 0;
    public String color = "auto";

    public Border(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "<w:" + type +
                " w:val=\"" + val +
                "\" w:sz=\"" + sz +
                "\" wx:bdrwidth=\"" + bdrwidth +
                "\" w:space=\"" + space +
                "\" w:color=\"" + color +
                "\"/>";
    }
}
