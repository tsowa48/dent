package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper implements Helper<Object> {
    private static final SimpleDateFormat standartFormat = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat format;

    public DateHelper(SimpleDateFormat format) {
        this.format = format;
    }

    @Override
    public Object apply(Object context, Options options) {
        Date date = new Date();
        if (context instanceof Date) {
            date = (Date) context;
        } else if (context instanceof String) {
            try {
                date = standartFormat.parse(context.toString());
            } catch (Exception ex) {
            }
        }
        return format.format(date);
    }
}
