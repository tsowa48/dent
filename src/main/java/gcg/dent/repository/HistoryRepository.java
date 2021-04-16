package gcg.dent.repository;

import gcg.dent.entity.History;
import gcg.dent.entity.Patient;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class HistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Get medical {@link History} by {@link Patient} id
     * @param pid {@link Patient} id
     * @return list of {@link History}
     */
    @Transactional
    @ReadOnly
    public List<History> getByPatientId(Long pid) {
        return entityManager
                .createQuery("select H from History H join fetch H.card C join fetch C.patient P where P.id = :pid", History.class)
                .setParameter("pid", pid)
                .getResultList();
    }
}
