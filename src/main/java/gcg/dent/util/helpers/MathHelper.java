package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

public class MathHelper implements Helper<Object> {
    private SIGN action;

    public MathHelper(SIGN action) {
        this.action = action;
    }

    @Override
    public Object apply(Object context, Options options) {
        if(context instanceof Double
                || options.param(0) instanceof Double) {
            Double param1 = Double.valueOf(context.toString());
            Double param2 = Double.valueOf(options.param(0).toString());
            switch(action) {
                case PLUS:
                default:
                    return param1 + param2;
                case MINUS:
                    return param1 - param2;
                case MULT:
                    return param1 * param2;
                case DIV:
                    return param1 / param2;
                case POW:
                    return Math.pow(param1, param2);
            }
        } else if(context instanceof Long
                || options.param(0) instanceof Long
                || context instanceof Integer
                || options.param(0) instanceof Integer) {
            Long param1 = Long.valueOf(context.toString());
            Long param2 = Long.valueOf(options.param(0).toString());
            switch(action) {
                case PLUS:
                default:
                    return param1 + param2;
                case MINUS:
                    return param1 - param2;
                case MULT:
                    return param1 * param2;
                case DIV:
                    return param1 / param2;
                case POW:
                    return Math.pow(param1, param2);
            }
        }
        return null;
    }

    public enum SIGN {
        PLUS,
        MINUS,
        MULT,
        DIV,
        POW
    }
}
