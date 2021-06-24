package gcg.word;

public class SizeCS extends Size {

    public SizeCS(int value) {
        super(value);
    }

    @Override
    public String toString() {
        return "<w:sz-cs w:val=\"" + value + "\"/>";
    }
}
