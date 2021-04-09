package gcg.dent.controller;

import gcg.dent.entity.Employee;
import gcg.dent.repository.EmployeeRepository;
import gcg.dent.repository.ScheduleRepository;
import gcg.dent.service.CalendarService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Controller
public class CalendarController {

    @Inject
    CalendarService calendarService;

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    ScheduleRepository scheduleRepository;

    @View("calendar")
    @Get
    public HttpResponse<HashMap<String, Object>> calendar() throws URISyntaxException {
        return calendar((short)0);
    }

    @View("calendar")
    @Get(uri = "/week/{week}")
    public HttpResponse<HashMap<String, Object>> calendar(Short week) throws URISyntaxException {
        boolean isEmpty = scheduleRepository.isEmpty();
        //if(isEmpty) {
            //return HttpResponse.temporaryRedirect(new URI("/reference"));
        //}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, week * 7);

        HashMap<String, Object> params = calendarService.get(calendar.getTime());

        List<Employee> employee = employeeRepository.getScheduled();
        params.put("doctors", employee);

        params.put("prev", week - 1);
        params.put("next", week + 1);
        return HttpResponse.ok(params);
    }
}
