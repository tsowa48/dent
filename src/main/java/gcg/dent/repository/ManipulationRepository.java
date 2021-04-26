package gcg.dent.repository;

import gcg.dent.entity.Manipulation;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ManipulationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Manipulation> getAll() {
        return entityManager
                .createQuery("select M from Manipulation M join fetch M.service S order by S.name, M.name", Manipulation.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Manipulation findById(Long id) {
        return entityManager.find(Manipulation.class, id);
    }

    @Transactional
    public Manipulation addRecord(Manipulation manipulation) {
        manipulation.setId(null);
        entityManager.persist(manipulation);
        return manipulation;
    }

    @Transactional
    public Manipulation update(Manipulation manipulation) {
        entityManager.merge(manipulation);
        return manipulation;
    }

    @Transactional
    public void removeById(Long id) {
        entityManager
                .createQuery("delete from Manipulation M where M.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}