package gcg.word;

import gcg.word.util.Rsid;

public class Paragraph {
    private Rsid rsid;

    private gcg.word.properties.Paragraph pPr;
    private Row r;

    public Paragraph(Rsid rsid, String fontName, int fontSize, boolean bold, JustifyContent justify, String text) {
        this.rsid = rsid;

        pPr = new gcg.word.properties.Paragraph(
                new Spacing(0, fontSize * 10, "auto"),//FIXME
                justify,
                new gcg.word.properties.Row(fontName, fontSize, bold));
        r = new Row(rsid.rsidRPr(), new gcg.word.properties.Row(fontName, fontSize, bold), text);
    }

    public Row getRow() {
        return r;
    }

    @Override
    public String toString() {
        return "<w:p wsp:rsidR=\"" + rsid.rsidR() + "\" wsp:rsidRPr=\"" + rsid.rsidRPr() + "\" wsp:rsidRDefault=\"" + rsid.rsidRDefault() + "\" wsp:rsidP=\"" + rsid.rsidP() + "\">" +
                pPr +
                r +
                "</w:p>";
    }
}
