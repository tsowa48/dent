package gcg.word.properties;

import gcg.word.Font;
import gcg.word.Size;
import gcg.word.SizeCS;

public class Row {
    private gcg.word.font.Row rFonts;
    private Font font;
    private boolean bold;
    private Size sz;
    private SizeCS sz_cs;

    public Row(String fontName, int size, boolean bold) {
        rFonts = new gcg.word.font.Row(fontName);
        font = new Font(fontName);
        this.bold = bold;
        sz = new Size(size);
        sz_cs = new SizeCS(size);
    }

    @Override
    public String toString() {
        return "<w:rPr>" +
                rFonts +
                font +
                (bold ? "<w:b/>" : "") +
                sz +
                sz_cs +
                "</w:rPr>";
    }
}
