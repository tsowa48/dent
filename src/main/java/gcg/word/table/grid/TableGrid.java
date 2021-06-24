package gcg.word.table.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TableGrid {
    private List<GridColumn> columns;

    public TableGrid(List<Integer> width) {
        columns = new ArrayList<>();
        for(Integer w : width) {
            columns.add(new GridColumn(w.intValue()));
        }
    }

    @Override
    public String toString() {
        return "<w:tblGrid>" +
                columns.stream()
                        .map(GridColumn::toString)
                        .collect(Collectors.joining()) +
                "</w:tblGrid>";
    }
}
