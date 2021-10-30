package gcg.dent.service;

import gcg.dent.dto.CalendarDates;
import gcg.dent.entity.Patient;
import gcg.dent.entity.Slot;
import gcg.dent.repository.SlotRepository;
import gcg.dent.util.ObjectUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class CalendarService {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d LLL");

    private static final int SLOT_SIZE = 30;//minutes

    @Inject
    SlotRepository slotRepository;

    @Inject
    ScheduleService scheduleService;

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

        List<CalendarDates> dates = new ArrayList<>();
        finish.add(Calendar.DAY_OF_WEEK, 1);
        for (Calendar c = (Calendar) start.clone(); c.before(finish); c.add(Calendar.DAY_OF_WEEK, 1)) {
            String format = dateFormat.format(c.getTime()).toLowerCase();
            Boolean today = ObjectUtils.compareDates(c.getTime(), new Date());
            String dayOfWeek = ObjectUtils.dow2str(c.getTime());
            dates.add(new CalendarDates(today, dayOfWeek, format));
        }
        params.put("dates", dates);

        Time[] globalSchedule = scheduleService.findFirstAndLast();
        List<String> times = new ArrayList<>();
        for (LocalTime t = globalSchedule[0].toLocalTime(); t.isBefore(globalSchedule[1].toLocalTime()); t = t.plusMinutes(SLOT_SIZE)) {
            times.add(String.format("%02d", t.getHour()) + ":" +
                    String.format("%02d", t.getMinute()));
        }
        params.put("times", times);
        params.put("times_size", times.size());

        StringBuilder htmlSlots = new StringBuilder();
        for (int dow = 0; dow < 7; dow++) {//day of week
            htmlSlots.append("<div id=\"dow_" + dow + "\" class=\"day\">");
            final int finalDow = dow;
            List<Slot> daySlots = slots.stream()
                    .filter(s -> ObjectUtils.dow(s.getDate()) == finalDow)
                    .collect(Collectors.toList());
            for (LocalTime t = globalSchedule[0].toLocalTime(); t.isBefore(globalSchedule[1].toLocalTime()); t = t.plusMinutes(SLOT_SIZE)) {
                final LocalTime finalT = t;
                boolean isWork = scheduleService.isWork(Time.valueOf(finalT), finalDow);
                if (!isWork) {
                    htmlSlots.append("<div class='slot disabled'></div>");
                    continue;
                }
                List<Slot> timeSlots = daySlots.stream()
                        .filter(s -> s.getTime().toLocalTime().compareTo(finalT) == 0)
                        .collect(Collectors.toList());
                Calendar temp = (Calendar) start.clone();
                temp.add(Calendar.DATE, dow);
                String slotDate = String.format("%d-%02d-%02d", temp.get(Calendar.YEAR), temp.get(Calendar.MONTH) + 1, temp.get(Calendar.DATE));
                htmlSlots.append("<div class='slot' date='" + slotDate + "' time='" + finalT.toString() + "' size='" + SLOT_SIZE + "' onclick=\"slot_modal(this);\">");
                timeSlots.forEach(ts -> {
                    if(ts.isEnabled()) {
                        Patient p = ts.getPatient();
                        String fio = p.getFio();
                        String phone = p.getPhone();
                        String note = ts.getNote();
                        htmlSlots.append("<div class='box pink' onclick=\"slot_modal(this);event.stopPropagation();\" sid='" + ts.getId() +
                                "' pid='" + p.getId() + "' doc='" + ts.getDoctor().getId() +
                                "'><span class='fio' style='display:none;'>" + fio + "</span>" +
                                "<span class='address' style='display:none;'>" + p.getAddress() + "</span>" +
                                "<span class='sex' style='display:none;'>" + (p.isMale()? "1":"0") + "</span>" +
                                "<span class='findOut' style='display:none;'>" + p.getFindOut().getId() + "</span>" +
                                "<span class='birth' style='display:none;'>" + p.getBirth() + "</span>" +
                                "<b>" + ObjectUtils.fio(fio) +
                                "</b><br><span class='phone'>" + phone + "</span>" +
                                "<br><span class='note'>" + (note == null ? "" : note) + "</span></div>");
                    } else {
                        htmlSlots.append("<div class='box pink' onclick=\"slot_modal(this);event.stopPropagation();\" sid='" + ts.getId() +
                                "' doc='" + ts.getDoctor().getId() + "'></div>");
                    }
                });
                htmlSlots.append("</div>");
            }
            htmlSlots.append("</div>");
        }

        params.put("slots", htmlSlots);
        params.put("slot_size", SLOT_SIZE);
        return params;
    }
}
