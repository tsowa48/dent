package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public class YesNoHelper implements Helper<Object> {

    @Override
    public Object apply(Object context, Options options) {
        if (context instanceof Boolean) {
            return (boolean)context ? "Да" :"Нет";
        }
        return "Нет";
    }
}
