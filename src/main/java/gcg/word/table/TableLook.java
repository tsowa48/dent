package gcg.word.table;

public class TableLook {
    private String value;

    public TableLook(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "<w:tblLook w:val=\"" + value + "\"/>";
    }
}
