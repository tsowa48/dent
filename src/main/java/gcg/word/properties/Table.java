package gcg.word.properties;

import gcg.word.table.TableLook;
import gcg.word.table.border.Border;
import gcg.word.table.border.Type;
import gcg.word.table.tblW;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
    private tblW width = new tblW();
    private TableLook tblLook = new TableLook("04A0");
    private List<Border> tblBorders = new ArrayList<>();

    public Table() {//TODO
        tblBorders.add(new Border(Type.TOP));
        tblBorders.add(new Border(Type.LEFT));
        tblBorders.add(new Border(Type.BOTTOM));
        tblBorders.add(new Border(Type.RIGHT));
        tblBorders.add(new Border(Type.INSIDE_H));
        tblBorders.add(new Border(Type.INSIDE_V));
    }

    @Override
    public String toString() {
        return "<w:tblPr>" +
                width +
                "<w:tblBorders>" +
                tblBorders.stream()
                        .map(Border::toString)
                        .collect(Collectors.joining()) +
                "</w:tblBorders>" +
                tblLook +
                "</w:tblPr>";
    }
}
