package gcg.word;

public class Text {
    private String value;

    public Text(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<w:t>" + value + "</w:t>";
    }
}
