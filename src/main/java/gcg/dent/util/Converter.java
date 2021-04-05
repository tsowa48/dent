package gcg.dent.util;

import java.util.Calendar;
import java.util.Date;

public class Converter {
    private static final String[] weekDays = new String[] {"", "вс", "пн", "вт", "ср", "чт", "пт", "сб"};

    private Converter() {}

    public static int dow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        if(dow > 1) {
            return dow - 2;
        } else {
            return 6;
        }
    }

    public static String dow2str(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int dow = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDays[dow];
    }

    public static String fio(String fio) {
        String[] f = fio.split(" ");
        for(int i = 1; i < f.length; i++) {
            f[i] = f[i].substring(0, 1) + ".";
        }
        return String.join(" ", f);
    }
}