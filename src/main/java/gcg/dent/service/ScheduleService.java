package gcg.dent.service;

import gcg.dent.entity.Schedule;
import gcg.dent.repository.ScheduleRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Time;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Singleton
public class ScheduleService {

    @Inject
    ScheduleRepository scheduleRepository;

    private List<Schedule> schedules;

    public boolean isWork(Time time, int dow) {
        return schedules.stream()
                .filter(s -> s.getDow() == dow &&
                        time.getTime() >= s.getStart().getTime() &&
                        time.getTime() < s.getFinish().getTime())
                .count() > 0;
    }

    public Time[] findFirstAndLast(int dow) {
        if(schedules == null) {
            schedules = scheduleRepository.getAll();
        }
        Schedule start = schedules.stream()
                .filter(t -> t.getDow() == dow)
                .min(Comparator.comparingLong(t -> t.getStart().getTime()))
                .get();
        Schedule finish = schedules.stream()
                .filter(t -> t.getDow() == dow)
                .max(Comparator.comparingLong(t -> t.getFinish().getTime()))
                .get();
        return new Time[] { start.getStart(), finish.getFinish() };
    }

    public Time[] findFirstAndLast() {
        if(schedules == null) {
            schedules = scheduleRepository.getAll();
        }
        Time start = schedules.stream()
                .map(Schedule::getStart)
                .min(Comparator.comparingLong(Date::getTime))
                .get();
        Time finish = schedules.stream()
                .map(Schedule::getFinish)
                .max(Comparator.comparingLong(Date::getTime))
                .get();
        return new Time[] { start, finish };
    }
}
