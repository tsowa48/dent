package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public class EmptyHelper implements Helper<Object> {

    @Override
    public Object apply(Object context, Options options) {
        return context == null ? "" : context;
    }
}
