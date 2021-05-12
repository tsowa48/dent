package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public class MathHelper implements Helper<Object> {
    private String action;

    public MathHelper(String action) {
        this.action = action;
    }

    @Override
    public Object apply(Object context, Options options) {
        if(context instanceof Double
                || options.param(0) instanceof Double) {
            Double param1 = Double.valueOf(context.toString());
            Double param2 = Double.valueOf(options.param(0).toString());
            switch(action) {
                case "+":
                default:
                    return param1 + param2;
                case "-":
                    return param1 - param2;
                case "*":
                    return param1 * param2;
                case "/":
                    return param1 / param2;
                case "^":
                    return Math.pow(param1, param2);
            }
        } else if(context instanceof Long
                || options.param(0) instanceof Long
                || context instanceof Integer
                || options.param(0) instanceof Integer) {
            Long param1 = Long.valueOf(context.toString());
            Long param2 = Long.valueOf(options.param(0).toString());
            switch(action) {
                case "+":
                default:
                    return param1 + param2;
                case "-":
                    return param1 - param2;
                case "*":
                    return param1 * param2;
                case "/":
                    return param1 / param2;
                case "^":
                    return Math.pow(param1, param2);
            }
        }
        return null;
    }
}
