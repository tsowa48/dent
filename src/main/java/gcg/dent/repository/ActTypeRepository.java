package gcg.dent.repository;

import gcg.dent.entity.ActType;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ActTypeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<ActType> getAll() {
        return entityManager
                .createQuery("select A from ActType A order by A.name", ActType.class)
                .getResultList();
    }

    @Transactional
    public ActType addActType(ActType actType) {
        actType.setId(null);
        entityManager.persist(actType);
        return actType;
    }

    @Transactional
    @ReadOnly
    public ActType findById(Long id) {
        return entityManager.find(ActType.class, id);
    }

    @Transactional
    public void removeById(Long id) {
        entityManager.createQuery("delete from ActType A where A.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public ActType update(ActType actType) {
        entityManager.merge(actType);
        return actType;
    }
}
