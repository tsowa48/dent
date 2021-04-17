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
    public List<Schedule> getAll() {
        return entityManager
                .createQuery("select S from Schedule S order by S.dow", Schedule.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Boolean isEmpty() {
        return entityManager
                .createQuery("select S from Schedule S", Schedule.class)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }

    @Transactional
    @ReadOnly
    public List<Schedule> getByDow(Integer dow){
        return entityManager
                .createQuery("select S from Schedule S where S.dow = :dow", Schedule.class)
                .setParameter("dow", dow)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Schedule findById(Long id) {
        return entityManager.find(Schedule.class, id);
    }

    @Transactional
    public Schedule addRecord(Schedule schedule)
    {
        schedule.setId(null);
        entityManager.persist(schedule);
        return schedule;
    }

    @Transactional
    public Schedule update(Schedule schedule)
    {
        entityManager.merge(schedule);
        return schedule;
    }

    @Transactional
    public void removeById(Long id){
        entityManager
                .createQuery("delete from Schedule S where S.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
