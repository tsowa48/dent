package gcg.word.table.grid;

public class GridColumn {
    private int width;

    public GridColumn(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "<w:gridCol w:w=\"" + width + "\"/>";
    }
}
