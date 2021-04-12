package gcg.dent.repository;

import gcg.dent.entity.Slot;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Singleton
public class SlotRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Slot makeSlot(Date date, Time time, Time size) {
        Slot slot = new Slot();
        slot.setDate(date);
        slot.setTime(time);
        slot.setSize(size);
        slot.setEnabled(true);
        return slot;
    }

    @Transactional
    @ReadOnly
    public Slot findById(Long id) {
        return entityManager.find(Slot.class, id);
    }

    @Transactional
    @ReadOnly
    public List<Slot> findByPeriod(Date start, Date end) {
        return entityManager
                .createQuery("select S from Slot S where S.date >= :dateStart and S.date <= :dateEnd order by S.date, S.time", Slot.class)
                .setParameter("dateStart", start)
                .setParameter("dateEnd", end)
                .getResultList();
    }

    @Transactional
    public void removeById(Long id) {
        entityManager.createQuery("delete from Slot S where S.id = :id")
        .setParameter("id", id)
        .executeUpdate();
    }
}
