package gcg.word.table;

import gcg.word.Paragraph;

public class TableColumn {
    private gcg.word.properties.TableColumn tcPr;
    private Paragraph p;

    public TableColumn(Paragraph p, int width) {
        this.tcPr = new gcg.word.properties.TableColumn(width);
        this.p = p;
    }

    public Paragraph getParagraph() {
        return p;
    }

    public int getWidth() {
        return tcPr.getWidth();
    }

    @Override
    public String toString() {
        return "<w:tc>" +
                tcPr +
                p +
                "</w:tc>";
    }
}
