package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.io.IOException;

public class NullableHelper implements Helper<Object> {
    @Override
    public Object apply(Object context, Options options) throws IOException {
        return (context == null ? "не указано" : context);
    }
}
