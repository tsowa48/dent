package gcg.word;

public class Font {
    private String value;

    public Font(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<wx:font wx:val=\"" + value + "\"/>";
    }
}
