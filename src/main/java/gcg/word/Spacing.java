package gcg.word;

import java.util.Optional;

public class Spacing {
    private int after;
    private int line;
    private String line_rule;

    public Spacing(int after, int line, String line_rule) {
        this.after = after;
        this.line = line;
        this.line_rule = line_rule;
    }

    @Override
    public String toString() {
        return "<w:spacing w:after=\"" + after + "\"" +
                " w:line=\"" + line + "\"" +
                " w:line-rule=\"" + line_rule + "\"/>";
    }
}
