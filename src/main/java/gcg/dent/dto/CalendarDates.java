package gcg.dent.dto;

public class CalendarDates {
    private Boolean today;
    private String dayOfWeek;
    private String day;

    public CalendarDates(Boolean today, String dayOfWeek, String day) {
        this.today = today;
        this.dayOfWeek = dayOfWeek;
        this.day = day;
    }

    public Boolean getToday() {
        return today;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDay() {
        return day;
    }
}
