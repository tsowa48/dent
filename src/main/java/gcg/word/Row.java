package gcg.word;

public class Row {
    private String rsidRPr;
    private gcg.word.properties.Row rPr;
    private Text t;

    public Row(String rsidRPr, gcg.word.properties.Row rPr, String text) {
        this.rsidRPr = rsidRPr;
        this.rPr = rPr;
        this.t = new Text(text);
    }

    @Override
    public String toString() {
        return "<w:r wsp:rsidRPr=\"" + rsidRPr + "\">" +
                rPr +
                t +
                "</w:r>";
    }
}
