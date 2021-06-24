package gcg.word.properties;

import gcg.word.table.TableColumnWidth;

public class TableColumn {
    private TableColumnWidth tcW;

    public TableColumn(int width) {
        tcW = new TableColumnWidth(width, "dxa");
    }

    public int getWidth() {
        return tcW.getWidth();
    }

    @Override
    public String toString() {
        return "<w:tcPr>" +
                tcW +
                "<w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"auto\"/>" +//TODO: create class
                "</w:tcPr>";
    }
}
