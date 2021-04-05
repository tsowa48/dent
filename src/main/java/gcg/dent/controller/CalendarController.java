package gcg.dent.controller;

import gcg.dent.entity.Client;
import gcg.dent.entity.Slot;
import gcg.dent.repository.SlotRepository;
import gcg.dent.util.Converter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller("/calendar")
public class CalendarController {

    @Inject
    SlotRepository slotRepository;

    @View("calendar")
    @Get(uri = "/")
    public HttpResponse calendar() {
        HashMap<String, Object> params = new HashMap<>();

        Date date = new Date();//new Date(2021 - 1900, 3, 7);

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
            String format = new SimpleDateFormat("d LLL").format(c.getTime());
            dates.add("<div class=\"date\" " +
                    (c.get(Calendar.DATE) == (new Date().getDate()) ? "style='background: rgb(202, 227, 242);'" : "")+
                    ">" + Converter.dow2str(c.getTime()) +
                    "<br><font style='font-weight:bold;font-size:16px;'>" +
                    format + "</font></div>");
        }
        params.put("dates", dates);

        StringBuilder htmlSlots = new StringBuilder();
        for(int dow = 0; dow < 7; dow++) {//day of week
            htmlSlots.append("<div id=\"slot." + dow + "\" class=\"day\">");
            final int finalDow = dow;
            List<Slot> daySlots = slots.stream()
                    .filter(s -> Converter.dow(s.getDate()) == finalDow)
                    .collect(Collectors.toList());
            for(int t = 0; t < 24; t++) {
                final int finalT = t;
                Optional<Slot> timeSlot = daySlots.stream()
                        .filter(s -> s.getTime() == finalT)
                        .findFirst();
                if(timeSlot.isPresent()) {
                    Client c = timeSlot.get().getClient();
                    htmlSlots.append("<div class='slot'><div class='box pink'><b>" +
                            Converter.fio(c.getFio()) + "</b><br><span>" + c.getPhone() + "</span></div></div>");
                } else {
                    //empty slot
                    htmlSlots.append("<div class='slot'>" + "</div>");
                }
            }
            htmlSlots.append("</div>");
        }

        params.put("slots", htmlSlots);

        return HttpResponse.ok(params);
    }
}
