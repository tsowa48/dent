package gcg.dent.service;

import gcg.dent.entity.Client;
import gcg.dent.entity.Slot;
import gcg.dent.repository.ScheduleRepository;
import gcg.dent.repository.SlotRepository;
import gcg.dent.util.ObjectUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class CalendarService {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d LLL");

    @Inject
    SlotRepository slotRepository;

    @Inject
    ScheduleRepository scheduleRepository;

    public HashMap<String, Object> get(Date date) {
        HashMap<String, Object> params = new HashMap<>();

        Calendar start = Calendar.getInstance();
        start.setFirstDayOfWeek(Calendar.MONDAY);
        start.setTime(date);
        int day = Calendar.MONDAY - start.get(Calendar.DAY_OF_WEEK);
        start.add(Calendar.DAY_OF_WEEK, day > 0 ? -6 : day);

        Calendar finish = Calendar.getInstance();
        finish.setFirstDayOfWeek(Calendar.MONDAY);
        finish.setTime(date);
        day = Calendar.SATURDAY - finish.get(Calendar.DAY_OF_WEEK) + 1;
        finish.add(Calendar.DAY_OF_WEEK, day == 7 ? 0 : day);

        List<Slot> slots = slotRepository.findByPeriod(start.getTime(), finish.getTime());

        List<String> dates = new ArrayList<>();
        finish.add(Calendar.DAY_OF_WEEK, 1);
        for(Calendar c = start; c.before(finish); c.add(Calendar.DAY_OF_WEEK, 1)) {
            String format = dateFormat.format(c.getTime());
            dates.add("<div class=\"date\" " +
                    (ObjectUtils.compareDates(c.getTime(), new Date()) ? "style='background: rgb(202, 227, 242);'" : "")+
                    ">" + ObjectUtils.dow2str(c.getTime()) +
                    "<br><font style='font-weight:bold;font-size:16px;'>" +
                    format + "</font></div>");
        }
        params.put("dates", dates);

        Time[] schedule = scheduleRepository.findFirstAndLast();
        List<String> times = new ArrayList<>();
        for(int t = schedule[0].getHours(); t < schedule[1].getHours(); t++) {
            times.add("<div class=\"time\"><b>" + String.format("%02d", t) + "</b>:00</div>");
        }
        params.put("times", times);
        params.put("times_size", times.size());

        StringBuilder htmlSlots = new StringBuilder();
        for(int dow = 0; dow < 7; dow++) {//day of week
            htmlSlots.append("<div id=\"slot." + dow + "\" class=\"day\">");
            final int finalDow = dow;
            List<Slot> daySlots = slots.stream()
                    .filter(s -> ObjectUtils.dow(s.getDate()) == finalDow)
                    .collect(Collectors.toList());
            for(int t = schedule[0].getHours(); t < schedule[1].getHours(); t++) {
                final int finalT = t;
                boolean isWork = scheduleRepository.isWork(new Time(finalT, 0, 0));
                if(!isWork) {
                    htmlSlots.append("<div class='slot disabled'>" + "</div>");
                    continue;
                }
                Optional<Slot> timeSlot = daySlots.stream()
                        .filter(s -> s.getTime() == finalT)
                        .findFirst();
                if(timeSlot.isPresent()) {
                    Client c = timeSlot.get().getClient();
                    htmlSlots.append("<div class='slot'><div class='box pink'><b>" +
                            ObjectUtils.fio(c.getFio()) + "</b><br><span>" + c.getPhone() + "</span></div></div>");
                } else {
                    //empty slot
                    String onClick = "show_modal(this, '#new_record');";
                    htmlSlots.append("<div class='slot' onclick=\"" + onClick + "\">" + "</div>");
                }
            }
            htmlSlots.append("</div>");
        }

        params.put("slots", htmlSlots);
        return params;
    }
}
