package gcg.dent.repository;

import gcg.dent.entity.Schedule;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ScheduleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Schedule> findAll() {
        return entityManager
                .createQuery("select S from Schedule S", Schedule.class)
                .getResultList();
    }
}
