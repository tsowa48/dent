package gcg.dent.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ObjectUtils {
    private static final String[] weekDays = new String[] {"", "вс", "пн", "вт", "ср", "чт", "пт", "сб"};
    public static final String[] weekDaysFull = new String[] {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    private ObjectUtils() {}

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
            if(f[i].length() > 0) {
                f[i] = f[i].substring(0, 1) + ".";
            }
        }
        return String.join(" ", f);
    }

    public static boolean compareDates(Date date1, Date date2) {
        boolean isEqual = date1.getDate() == date2.getDate();
        isEqual &= date1.getMonth() == date2.getMonth();
        isEqual &= date1.getYear() == date2.getYear();
        return isEqual;
    }

    public static boolean comparePhones(String phone1, String phone2) {
        String tmpPhone1 = phone1.replaceAll("[()\\- ]", "");
        String tmpPhone2 = phone2.replaceAll("[()\\- ]", "");
        return tmpPhone1.equals(tmpPhone2);
    }
}
