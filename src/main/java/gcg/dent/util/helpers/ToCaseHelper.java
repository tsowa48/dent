package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public class ToCaseHelper implements Helper<String> {
    private CASE toCase;

    public ToCaseHelper(CASE toCase) {
        this.toCase = toCase;
    }

    @Override
    public Object apply(String context, Options options) throws IOException {
        if(toCase == CASE.LOWER) {
            return context.toLowerCase();
        } else if(toCase == CASE.UPPER) {
            return context.toUpperCase();
        }
        return context;
    }

    public enum CASE {
        LOWER,
        UPPER
    }
}
