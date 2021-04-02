package gcg.dent.repository;

import gcg.dent.entity.Slot;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Singleton
public class SlotRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Slot makeSlot(Date date, Integer time, Integer size) {
        Slot slot = new Slot();
        slot.setDate(date);
        slot.setTime(time);
        slot.setSize(size);
        slot.setEnabled(true);
        entityManager.persist(slot);
        return slot;
    }

    public Slot findById(Long id) {
        return entityManager.find(Slot.class, id);
    }
}
