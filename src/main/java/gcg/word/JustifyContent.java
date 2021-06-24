package gcg.word;

public enum JustifyContent {
    CENTER("center"),
    BOTH("both"),
    RIGHT("right"),
    LEFT("left")
    ;
    private String value;

    JustifyContent(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<w:jc w:val=\"" + value + "\"/>";
    }
}
