package gcg.dent.controller;

import gcg.dent.service.CalendarService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Controller("/calendar")
public class CalendarController {

    @Inject
    CalendarService calendarService;

    @View("calendar")
    @Get(uri = "/{week}")
    public HttpResponse<HashMap<String, Object>> calendar(Optional<Integer> week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, week.orElse(0) * 7);

        HashMap<String, Object> params = calendarService.get(calendar.getTime());

        params.put("prev", week.orElse(0) - 1);
        params.put("next", week.orElse(0) + 1);
        return HttpResponse.ok(params);
    }
}
