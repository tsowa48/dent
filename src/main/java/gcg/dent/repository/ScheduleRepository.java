package gcg.dent.repository;

import gcg.dent.entity.Schedule;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Time;
import java.util.List;

@Singleton
public class ScheduleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public Time[] findFirstAndLast() {
        Object[] times = (Object[])entityManager
                .createNativeQuery("select min(start), max(\"end\") from schedule")
                .getSingleResult();
        return new Time[] { (Time)times[0], (Time)times[1] };
    }

    @Transactional
    @ReadOnly
    public List<Schedule> findAll() {
        return entityManager
                .createQuery("select S from Schedule S order by S.start", Schedule.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public boolean isWork(Time time) {
        Integer count = (Integer)entityManager
                .createNativeQuery("select count(*)::::int from schedule where (:time) >= start and (:time) < \"end\";")
                .setParameter("time", time)
                .getSingleResult();
        return count > 0;
    }
}
