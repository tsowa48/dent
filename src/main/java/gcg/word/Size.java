package gcg.word;

public class Size {
    protected int value;

    public Size(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<w:sz w:val=\"" + value + "\"/>";
    }
}
