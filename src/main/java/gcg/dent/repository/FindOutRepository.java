package gcg.dent.repository;

import gcg.dent.entity.FindOut;
import gcg.dent.entity.Schedule;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class FindOutRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<FindOut> getAll() {
        return entityManager
                .createQuery("select F from FindOut F", FindOut.class)
                .getResultList();
    }
}
