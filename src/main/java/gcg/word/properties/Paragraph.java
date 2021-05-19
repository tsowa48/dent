package gcg.word.properties;

import gcg.word.JustifyContent;
import gcg.word.Spacing;

public class Paragraph {
    private Spacing spacing;
    private JustifyContent jc;
    private Row rPr;

    public Paragraph(Spacing spacing, JustifyContent jc, Row rPr) {
        this.spacing = spacing;
        this.jc = jc;
        this.rPr = rPr;
    }

    @Override
    public String toString() {
        return "<w:pPr>" +
                spacing +
                jc +
                rPr +
                "</w:pPr>";
    }
}
