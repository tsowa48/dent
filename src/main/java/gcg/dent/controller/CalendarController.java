package gcg.dent.controller;

import gcg.dent.entity.Employee;
import gcg.dent.repository.EmployeeRepository;
import gcg.dent.service.CalendarService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;

import javax.inject.Inject;
import java.util.*;

@Controller
public class CalendarController {

    @Inject
    CalendarService calendarService;

    @Inject
    EmployeeRepository employeeRepository;

    @View("calendar")
    @Get
    public HttpResponse<HashMap<String, Object>> calendar() {
        return calendar(0);
    }

    @View("calendar")
    @Get(uri = "/{week}")
    public HttpResponse<HashMap<String, Object>> calendar(Integer week) {
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
