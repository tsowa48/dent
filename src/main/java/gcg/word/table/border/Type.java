package gcg.word.table.border;

public enum Type {
    TOP("top"),
    LEFT("left"),
    BOTTOM("bottom"),
    RIGHT("right"),
    INSIDE_H("insideH"),
    INSIDE_V("insideV")
    ;

    private String type;
    Type(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
