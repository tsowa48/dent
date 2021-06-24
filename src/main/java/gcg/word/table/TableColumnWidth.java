package gcg.word.table;

public class TableColumnWidth {
    private int width;
    private String type;

    public TableColumnWidth(int width, String type) {
        this.width = width;
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString() {
        return "<w:tcW w:w=\"" + width + "\" w:type=\"" + type + "\"/>";
    }
}
