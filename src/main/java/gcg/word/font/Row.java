package gcg.word.font;

public class Row {
    private String ascii;
    private String h_ansi;//h-ansi

    public Row(String ascii) {
        this.ascii = ascii;
        this.h_ansi = ascii;//FIXME???
    }

    @Override
    public String toString() {
        return "<w:rFonts w:ascii=\"" + ascii + "\" w:h-ansi=\"" + h_ansi + "\"/>";
    }
}
