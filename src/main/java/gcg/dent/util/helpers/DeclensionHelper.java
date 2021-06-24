package gcg.dent.util.helpers;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeclensionHelper implements Helper<String> {

    @Override
    public Object apply(String declension, Options options) {
        String fio = options.param(0);
        Stream<String> parts = Arrays.stream(fio.split(" "));
        switch(declension.toLowerCase()) {
            case "genitive":
                parts = parts.map(p -> p = genitive(p));//кого? чего?
                break;
            case "dative":
                parts = parts.map(p -> p = dative(p));//кому? чему?
                break;
            case "accusative":
                parts = parts.map(p -> p = accusative(p));//кого? что?
                break;
            case "instrumental":
                parts = parts.map(p -> p = instrumental(p));//кем? чем?
                break;
            case "prepositional":
                parts = parts.map(p -> p = prepositional(p));//о ком? о чём?
            default:
        }
        return parts.collect(Collectors.joining(" "));
    }

    private static String genitive(String part) {
        if(part.endsWith("в") || part.endsWith("ч") || part.endsWith("н") || part.endsWith("р")) {
            part += "а";
        } else if(part.endsWith("ва")) {
            part = part.substring(0, part.length() - 1) + "ой";
        } else if(part.endsWith("на") || part.endsWith("ра")) {
            part = part.substring(0, part.length() - 1) + "ы";
        } else if(part.endsWith("я")) {
            part = part.substring(0, part.length() - 1) + "и";
        } else if(part.endsWith("й")) {
            part = part.substring(0, part.length() - 1) + "я";
        } else if(part.endsWith("о")) {
            //do nothing
        } else if(part.endsWith("ел")) {
            part = part.substring(0, part.length() - 1) + "ла";
        } else {
            part += "_";
        }
        return part;
    }

    private static String dative(String part) {
        return part;
    }

    private static String accusative(String part) {
        return part;
    }

    private static String instrumental(String part) {
        return part;
    }

    private static String prepositional(String part) {
        return part;
    }
}
