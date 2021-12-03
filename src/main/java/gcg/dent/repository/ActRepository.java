package gcg.dent.repository;

import gcg.dent.entity.Act;
import gcg.dent.entity.ActService;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ActRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Act update(Act act) {
        Act result = entityManager.merge(act);
        entityManager.flush();
        return result;
    }

    @Transactional
    public boolean batchUpdate(List<ActService> actServices) {
        if(actServices.size() > 0) {
            entityManager
                    .createNativeQuery("delete from act_service where aid=(:aid)")
                    .setParameter("aid", actServices.get(0).getAct().getId())
                    .executeUpdate();
        }
        actServices.forEach(as -> {
            entityManager
                    .createNativeQuery("insert into act_service(sid, aid, amount, date) values (:sid, :aid, :amount, :date);")
                    .setParameter("sid", as.getService().getId())
                    .setParameter("aid", as.getAct().getId())
                    .setParameter("amount", as.getAmount())
                    .setParameter("date", as.getDate())
                    .executeUpdate();
        });
        entityManager.flush();
        return true;
    }

    @Transactional
    @ReadOnly
    public Act get(Long id) {
        return entityManager.find(Act.class, id);
    }

    @Transactional
    public boolean delete(Long id) {
        try {
            entityManager
                    .createNativeQuery("delete from act_service where aid = (:aid)")
                    .setParameter("aid", id)
                    .executeUpdate();
            entityManager
                    .createNativeQuery("delete from act where id = (:aid)")
                    .setParameter("aid", id)
                    .executeUpdate();
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
