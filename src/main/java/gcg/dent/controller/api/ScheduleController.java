package gcg.dent.controller.api;

import gcg.dent.entity.Schedule;
import gcg.dent.repository.ScheduleRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller("/api/schedule")
public class ScheduleController {

    @Inject
    ScheduleRepository scheduleRepository;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Schedule get(Long id) {
        return scheduleRepository.findById(id);
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Schedule> getAllSchedule() {
        return scheduleRepository.getAll();
    }

    @Get(uri = "/dow/{dow}", produces = MediaType.APPLICATION_JSON)
    public List<Schedule> getByDow(Integer dow) {
        return scheduleRepository.getByDow(dow);
    }

    @Post(produces = MediaType.APPLICATION_JSON)
    public Schedule add(Schedule schedule) {
        return scheduleRepository.addRecord(schedule);
    }

    @Put(produces = MediaType.APPLICATION_JSON)
    public Schedule change(Schedule schedule) {
        return scheduleRepository.update(schedule);
    }

    @Delete(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse remove(Long id) {
        scheduleRepository.removeById(id);
        return HttpResponse.ok();
    }

}