package gcg.dent.repository;

import gcg.dent.entity.Service;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class ServiceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @ReadOnly
    public List<Service> getAll() {
        return entityManager
                .createQuery("select S from Service S order by S.actType.name, S.name", Service.class)
                .getResultList();
    }

    @Transactional
    @ReadOnly
    public Service findById(Long id) {
        return entityManager.find(Service.class, id);
    }

    @Transactional
    public Service addRecord(Service service) {
        service.setId(null);
        entityManager.persist(service);
        return service;
    }

    @Transactional
    public Service update(Service service) {
        entityManager.merge(service);
        return service;
    }

    @Transactional
    public void removeById(Long id) {
        entityManager
                .createQuery("delete from Service S where S.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}