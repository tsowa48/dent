package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JoinHelper implements Helper<List> {
    private String separator;

    public JoinHelper(String separator) {
        this.separator = separator;
    }

    @Override
    public String apply(List elements, Options options) {
        String field = options.param(0);
        List<String> values = new ArrayList<>();
        if(field != null) {
            try {
                String[] fields = field.split("[.]");
                for(int e = 0; e < elements.size(); e++) {
                    Object element = elements.get(e);
                    for (int f = 0; f < fields.length; f++) {
                        Field subField = element.getClass().getDeclaredField(fields[f]);
                        boolean isAccessible = subField.isAccessible();
                        if (!isAccessible) {
                            subField.setAccessible(true);
                        }

                        element = subField.get(element);

                        if (!isAccessible) {
                            subField.setAccessible(false);
                        }
                    }
                    values.add(element.toString());
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return String.join(separator, values);
    }
}
