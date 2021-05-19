package gcg.word.table;

import gcg.word.util.Rsid;

import java.util.List;
import java.util.stream.Collectors;

public class TableRow {
    private Rsid rsid;
    private List<TableColumn> tc;

    public TableRow(Rsid rsid, List<TableColumn> tc) {
        this.rsid = rsid;
        this.tc = tc;
    }

    public List<TableColumn> getColumns() {
        return tc;
    }

    @Override
    public String toString() {
        return "<w:tr wsp:rsidR=\"" + rsid.rsidR() + "\" wsp:rsidRPr=\"" + rsid.rsidRPr() + "\" wsp:rsidTr=\"" + rsid.rsidTr() + "\">" +
                tc.stream()
                        .map(TableColumn::toString)
                        .collect(Collectors.joining()) +
                "</w:tr>";
    }
}
